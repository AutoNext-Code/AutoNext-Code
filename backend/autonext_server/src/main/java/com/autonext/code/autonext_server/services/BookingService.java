package com.autonext.code.autonext_server.services;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.dto.MapBookingDTO;
import com.autonext.code.autonext_server.exceptions.BookingNotFoundException;
import com.autonext.code.autonext_server.exceptions.CarNotExistsException;
import com.autonext.code.autonext_server.exceptions.OverlappingBookingException;
import com.autonext.code.autonext_server.exceptions.ParkingLimitOverpassException;
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

  @Autowired
  private EmailTemplateService emailTemplateService;

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

  public Page<Booking> getFilteredBookingsPaged(Pageable pageable, LocalDate date, Integer workCenterId, Integer carId) {
    int userId = getAuthenticatedUserId();
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


  public void createBooking(MapBookingDTO dto) throws Exception {

    int userId = getAuthenticatedUserId();
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

    int reservasHoy = bookingRepository.countByUserAndDate(user, dto.getDate());
    
    boolean solapa = bookingRepository.existsOverlappingBooking(
      user,
      dto.getDate(),
      dto.getStartTime(),  
      dto.getEndTime()   
  );
  
    if (solapa) {
      throw new OverlappingBookingException(
          "Ya existe una reserva que cruza con el horario solicitado: "
          + dto.getStartTime() + " – " + dto.getEndTime()
      );
    }

    final int LIMITE_DIARIO = 2;
    if (reservasHoy >= LIMITE_DIARIO) {
        throw new ParkingLimitOverpassException(
            "Ha superado el límite de " + LIMITE_DIARIO + " reservas para el día " + dto.getDate()
        );
    }

    Car car = carRepository.findById(dto.getCarId())
        .orElseThrow(() -> new CarNotExistsException("La matrícula no está registrada"));
    ParkingSpace space = parkingSpaceRepository.findById(dto.getParkingSpaceId())
        .orElseThrow(() -> new ParkingSpaceNotExistsException("La plaza no está registrada"));
    bookingValidators.validate(dto, space);
    WorkCenter wc = space.getParkingLevel().getWorkCenter();

    Booking booking = new Booking(
        dto.getStartTime(),
        dto.getEndTime(),
        dto.getDate(),
        user,
        car
    );
    booking.setParkingSpace(space);
    booking.setWorkCenter(wc);
    bookingRepository.save(booking);
    emailTemplateService.notifyUserOnBookingCreation(booking, dto);
  }

  public void updateConfirmationStatus(int id, ConfirmationStatus confirmationStatus) throws Exception {
    int userId = getAuthenticatedUserId();

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

  public void cancelBooking(int bookingId) {
    int userId = getAuthenticatedUserId();

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
    emailTemplateService.notifyUserOnBookingCancellation(booking);
  }

  private int getAuthenticatedUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Object principal = authentication.getPrincipal();

    if (principal instanceof User user) {
      return user.getId();
    }

    throw new SecurityException("Usuario no autenticado correctamente");
  }

  public String checkIfUserCanBook(LocalDate date, LocalTime startHour, LocalTime endHour) {

    return "";

  }

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

