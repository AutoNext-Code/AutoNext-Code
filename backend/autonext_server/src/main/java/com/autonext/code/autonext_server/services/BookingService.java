package com.autonext.code.autonext_server.services;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.dto.MapBookingDTO;
import com.autonext.code.autonext_server.exceptions.BookingNotFoundException;
import com.autonext.code.autonext_server.exceptions.CarNotExistsException;
import com.autonext.code.autonext_server.exceptions.ParkingSpaceNotExistsException;
import com.autonext.code.autonext_server.exceptions.UserNotFoundException;
import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.Car;
import com.autonext.code.autonext_server.models.ParkingSpace;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.models.WorkCenter;
import com.autonext.code.autonext_server.models.enums.BookingStatus;
import com.autonext.code.autonext_server.models.enums.ConfirmationStatus;
import com.autonext.code.autonext_server.repositories.BookingRepository;
import com.autonext.code.autonext_server.repositories.CarRepository;
import com.autonext.code.autonext_server.repositories.ParkingSpaceRepository;
import com.autonext.code.autonext_server.repositories.UserRepository;
import com.autonext.code.autonext_server.services.helpers.BookingValidators;
import com.autonext.code.autonext_server.specifications.BookingSpecifications;

@Service
public class BookingService {

  private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

  @Autowired
  private BookingValidators bookingValidators;

  @Autowired
  private CarRepository carRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BookingRepository bookingRepository;

  @Autowired
  private ParkingSpaceRepository parkingSpaceRepository;

  public Page<Booking> getFilteredBookingsPaged(int userId,
      Pageable pageable,
      LocalDate date,
      Integer workCenterId,
      Integer carId) {
    Specification<Booking> spec = buildBookingFilter(userId, date, workCenterId, carId);
    return bookingRepository.findAll(spec, pageable);
  }

  private Specification<Booking> buildBookingFilter(
      int userId,
      LocalDate date,
      Integer workCenterId,
      Integer carId) {
    return Specification.where(BookingSpecifications.hasUserId(userId))
        .and(BookingSpecifications.hasDate(date))
        .and(BookingSpecifications.hasWorkCenterId(workCenterId))
        .and(BookingSpecifications.hasCarId(carId));
  }

  public Booking getBookingById(int id) {
    return bookingRepository.findById(id).orElse(null);
  }

  public void createBooking(MapBookingDTO mapBookingDTO, int userId) {
    logger.info(
        "Datos recibidos: Fecha: {}, Hora de inicio: {}, Hora de fin: {}, ID del coche: {}, ID de la plaza: {}, ID de usuario: {}",
        mapBookingDTO.getDate(), mapBookingDTO.getStartTime(), mapBookingDTO.getEndTime(),
        mapBookingDTO.getCarId(), mapBookingDTO.getParkingSpaceId(), userId);

    Car car = carRepository.findById(mapBookingDTO.getCarId())
        .orElseThrow(() -> new CarNotExistsException("La matrícula no está registrada"));

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

    ParkingSpace parkingSpace = parkingSpaceRepository.findById(mapBookingDTO.getParkingSpaceId())
        .orElseThrow(() -> new ParkingSpaceNotExistsException("La plaza no está registrada"));

    bookingValidators.validate(mapBookingDTO, parkingSpace);

    WorkCenter workCenter = parkingSpace.getParkingLevel().getWorkCenter();

    Booking booking = new Booking(
        mapBookingDTO.getStartTime(),
        mapBookingDTO.getEndTime(),
        mapBookingDTO.getDate(),
        user,
        car);

    booking.setParkingSpace(parkingSpace);
    booking.setWorkCenter(workCenter);

    bookingRepository.save(booking);
  }

  public void updateConfirmationStatus(int id, int userId, ConfirmationStatus confirmationStatus) throws Exception {
    Booking booking = bookingRepository.findById(id)
        .orElseThrow(() -> new BookingNotFoundException("Reserva no encontrada"));

    if (booking.getUser().getId() != userId) {
      throw new UserNotFoundException("Usuario no autorizado");
    }

    switch (confirmationStatus) {
      case Confirmed:
        if (booking.getConfirmationStatus() != ConfirmationStatus.PendingConfirmation) {
          throw new IllegalStateException("La reserva no está en estado de confirmación pendiente");
        }
        booking.setConfirmationStatus(ConfirmationStatus.Confirmed);
        booking.setStatus(BookingStatus.Active);
        break;

      case Expired:
        booking.setConfirmationStatus(ConfirmationStatus.Expired);
        booking.setStatus(BookingStatus.Strike);
        break;

      case Inactive:
        booking.setConfirmationStatus(ConfirmationStatus.Inactive);
        booking.setStatus(BookingStatus.Pending);
        break;

      case PendingConfirmation:
        booking.setConfirmationStatus(ConfirmationStatus.PendingConfirmation);
        break;

      default:
        throw new IllegalStateException("Estado de confirmación no reconocido");
    }

    bookingRepository.save(booking);
  }

  public void cancelBooking(int bookingId, int userId) {
    Booking booking = bookingRepository.findById(bookingId)
        .orElseThrow(() -> new BookingNotFoundException("Reserva no encontrada"));

    if (booking.getUser().getId() != userId) {
      throw new UserNotFoundException("No tienes permiso para cancelar esta reserva");
    }

    if (booking.getConfirmationStatus() != ConfirmationStatus.Inactive) {
      throw new IllegalStateException("La reserva no puede ser cancelada porque ya está en proceso de confirmación");
    }

    booking.setStatus(BookingStatus.Cancelled);
    booking.setConfirmationStatus(ConfirmationStatus.Expired);

    bookingRepository.save(booking);
  }

  /*
   * GUARDADO PARA EL ADMIN
   * public void updateBookingState(int id, int userId, BookingStatus
   * bookingStatus) throws Exception {
   * Booking booking = bookingRepository.findById(id)
   * .orElseThrow(() -> new BookingNotFoundException("Reserva no encontrada"));
   * 
   * if (booking.getUser().getId() != userId) {
   * throw new UserNotFoundException("Usuario no encontrado");
   * }
   * 
   * booking.setStatus(bookingStatus);
   * bookingRepository.save(booking);
   * 
   * }
   */
}
