package com.autonext.code.autonext_server.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
import com.autonext.code.autonext_server.exceptions.ParkingSpaceOccupiedException;
import com.autonext.code.autonext_server.exceptions.UserNotFoundException;
import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.Car;
import com.autonext.code.autonext_server.models.ParkingSpace;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.models.WorkCenter;
import com.autonext.code.autonext_server.models.enums.BookingStatus;
import com.autonext.code.autonext_server.repositories.BookingRepository;
import com.autonext.code.autonext_server.repositories.CarRepository;
import com.autonext.code.autonext_server.repositories.ParkingSpaceRepository;
import com.autonext.code.autonext_server.repositories.UserRepository;
import com.autonext.code.autonext_server.specifications.BookingSpecifications;

@Service
public class BookingService {

  private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

  @Autowired
  private CarRepository carRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BookingRepository bookingRepository;
    public List<Booking> getFilteredBookings(int userId,
            LocalDate date,
            String delegation,
            String carPlate,
            String plugType,
            String floor,
            String startTime,
            String endTime) {
        Specification<Booking> spec = buildBookingFilter(userId, date, delegation, carPlate, plugType, floor, startTime,
                endTime);
        return bookingRepository.findAll(spec);
    }

    public Page<Booking> getFilteredBookingsPaged(int userId,
            Pageable pageable,
            LocalDate date,
            String delegation,
            String carPlate) {
        Specification<Booking> spec = buildBookingFilter(userId, date, delegation, carPlate, null, null, null, null);
        return bookingRepository.findAll(spec, pageable);
    }

  @Autowired
  private ParkingSpaceRepository parkingSpaceRepository;

  private Specification<Booking> buildBookingFilter(int userId, LocalDate date, String delegation, String carPlate,
      String plugType, String floor, String startTime, String endTime) {
    return Specification.where(BookingSpecifications.hasUserId(userId))
        .and(BookingSpecifications.hasDate(date))
        .and(BookingSpecifications.hasDelegation(delegation))
        .and(BookingSpecifications.hasCarPlate(carPlate))
        .and(BookingSpecifications.hasPlugType(plugType))
        .and(BookingSpecifications.hasFloor(floor))
        .and(BookingSpecifications.hasStartTime(startTime))
        .and(BookingSpecifications.hasEndTime(endTime));
  }

  public List<Booking> getFilteredBookings(int userId, LocalDate date, String delegation, String carPlate) {
    Specification<Booking> spec = buildBookingFilter(userId, date, delegation, carPlate, null, null, null, null);
    return bookingRepository.findAll(spec);
  }

  public Page<Booking> getFilteredBookingsPaged(int userId, Pageable pageable,
      LocalDate date, String delegation, String carPlate,
      String plugType, String floor, String startTime, String endTime) {
    Specification<Booking> spec = buildBookingFilter(userId, date, delegation, carPlate, plugType, floor, startTime,
        endTime);
    return bookingRepository.findAll(spec, pageable);
  }

  public Booking getBookingById(int id) {
    return bookingRepository.findById(id).orElse(null);
  }

  public void createBooking(MapBookingDTO mapBookingDTO, int userId) {
    try {
      logger.info(
          "Datos recibidos: Fecha: {}, Hora de inicio: {}, Hora de fin: {}, ID del coche: {}, ID de la plaza: {}, ID de usuario: {}",
          mapBookingDTO.getDate(), mapBookingDTO.getStartTime(), mapBookingDTO.getEndTime(),
          mapBookingDTO.getCarId(), mapBookingDTO.getParkingSpaceId(), userId);

      // Obtener las entidades correspondientes usando los IDs del DTO
      Optional<Car> optionalCar = carRepository.findById(mapBookingDTO.getCarId());
      Optional<User> optionalUser = userRepository.findById(userId);
      Optional<ParkingSpace> optionalSpace = parkingSpaceRepository.findById(mapBookingDTO.getParkingSpaceId());

      // Comprobación de existencia
      if (!optionalSpace.isPresent()) {
        throw new ParkingSpaceNotExistsException("La plaza no está registrada");
      }
      if (!optionalUser.isPresent()) {
        throw new UserNotFoundException("Usuario No Encontrado");
      }
      if (!optionalCar.isPresent()) {
        throw new CarNotExistsException("La matrícula no está registrada");
      }

      ParkingSpace parkingSpace = optionalSpace.get();

      // Verificar si la plaza está ocupada en el horario deseado
      List<Booking> bookings = bookingRepository.findAllReservationsByDateAndSpace(mapBookingDTO.getDate(),
          parkingSpace);
      for (Booking booking : bookings) {
        boolean overlap = !(mapBookingDTO.getEndTime().isBefore(booking.getStartTime()) ||
            mapBookingDTO.getStartTime().isAfter(booking.getEndTime()));

        if (overlap && booking.getStatus() != BookingStatus.Active) {
          throw new ParkingSpaceOccupiedException("La plaza está ocupada en el horario seleccionado");
        }
      }

      // Crear la reserva
      Car car = optionalCar.get();
      User user = optionalUser.get();

      // Aquí obtenemos el WorkCenter de la ParkingSpace, como lo habíamos discutido
      WorkCenter workCenter = parkingSpace.getParkingLevel().getWorkCenter();

      // Creamos la entidad de Booking
      Booking booking = new Booking(mapBookingDTO.getStartTime(), mapBookingDTO.getEndTime(), mapBookingDTO.getDate(), user, car);
      booking.setParkingSpace(parkingSpace);
      booking.setWorkCenter(workCenter); // Asociamos el WorkCenter de la ParkingSpace a la reserva

      // Guardamos la reserva en la base de datos
      bookingRepository.save(booking);
    } catch (Exception e) {
      logger.error("Error al crear la reserva", e);
      throw new RuntimeException("Error al procesar la reserva"); // Lanza una excepción adecuada para el controlador
    }
  }

  public void updateBookingState(int id, int userId, BookingStatus bookingStatus) throws Exception {
    Booking booking = bookingRepository.getReferenceById(id);

    try {

      if (booking == null) {
        throw new BookingNotFoundException("Reserva no encontrada");
      }

      if (booking.getUser().getId() != userId) {
        throw new UserNotFoundException("Usuario no encontrado");
      }

      booking.setStatus(bookingStatus);
      bookingRepository.save(booking) ;

    } catch (Exception e) {
      throw new Exception("Error desconocido");
    }
  }
}
