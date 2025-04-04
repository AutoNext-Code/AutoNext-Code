package com.autonext.code.autonext_server.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.autonext.code.autonext_server.dto.BookingDTO;
import org.springframework.web.bind.annotation.RequestBody;
import com.autonext.code.autonext_server.dto.MapBookingDTO;
import com.autonext.code.autonext_server.exceptions.BookingNotFoundException;
import com.autonext.code.autonext_server.exceptions.CarNotExistsException;
import com.autonext.code.autonext_server.exceptions.ParkingSpaceNotExistsException;
import com.autonext.code.autonext_server.exceptions.ParkingSpaceOccupiedException;
import com.autonext.code.autonext_server.exceptions.UserNotFoundException;
import com.autonext.code.autonext_server.mapper.BookingMapper;
import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.models.enums.ConfirmationStatus;
import com.autonext.code.autonext_server.services.BookingService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

  private final BookingService bookingService;

  public BookingController(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @GetMapping("/booking-list")
  @SecurityRequirement(name = "bearerAuth")
  public Page<BookingDTO> getBookingsByUser(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "date") String sortBy,
      @RequestParam(defaultValue = "true") boolean ascending,
      @RequestParam(required = false) LocalDate date,
      @RequestParam(required = false) Integer workCenterId,
      @RequestParam(required = false) Integer carId) {

    int userId = getAuthenticatedUserId();
    PageRequest pageable = buildPageRequest(page, sortBy, ascending);

    Page<Booking> bookings = bookingService.getFilteredBookingsPaged(
        userId, pageable, date, workCenterId, carId);

    List<BookingDTO> bookingDTOs = bookings.getContent().stream()
        .map(BookingMapper::toDTO)
        .toList();

    return new PageImpl<>(bookingDTOs, pageable, bookings.getTotalElements());
  }

  @PostMapping("")
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public ResponseEntity<String> createBooking(@Valid @RequestBody MapBookingDTO booking) {

    try {

      int userId = getAuthenticatedUserId();

      bookingService.createBooking(booking, userId);

      return ResponseEntity.ok("Reserva registrada correctamente");

    } catch (ParkingSpaceNotExistsException psne) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La plaza no existe");
    } catch (CarNotExistsException cpne) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El coche no existe");
    } catch (UserNotFoundException unf) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario no existe");
    } catch (ParkingSpaceOccupiedException pso) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("La plaza ya esta ocupada");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
    }
  }

  @PutMapping("/{id}/confirmation")
  @SecurityRequirement(name = "bearerAuth")
  public ResponseEntity<String> updateConfirmationStatus(
      @PathVariable int id,
      @RequestParam ConfirmationStatus confirmationStatus) {

    try {
      int userId = getAuthenticatedUserId();
      bookingService.updateConfirmationStatus(id, userId, confirmationStatus);
      return ResponseEntity.ok("Estado de confirmación actualizado correctamente");
    } catch (BookingNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva no encontrada");
    } catch (UserNotFoundException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No puedes modificar esta reserva");
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
    }
  }

  @PutMapping("/{id}/cancel")
  @SecurityRequirement(name = "bearerAuth")
  public ResponseEntity<String> cancelBooking(@PathVariable int id) {
    try {
      int userId = getAuthenticatedUserId();
      bookingService.cancelBooking(id, userId);
      return ResponseEntity.noContent().build();
    } catch (BookingNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva no encontrada");
    } catch (UserNotFoundException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No puedes cancelar esta reserva");
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  /*
   * GUARDADO PARA EL ADMIN
   * 
   * @PutMapping("/{id}")
   * public ResponseEntity<String> updateBooking(
   * 
   * @RequestParam int id,
   * 
   * @RequestParam BookingStatus bookingStatus) throws Exception {
   * try {
   * int userId = getAuthenticatedUserId();
   * bookingService.updateBookingState(id, userId, bookingStatus);
   * return ResponseEntity.ok("Reserva modificada correctamente");
   * } catch (BookingNotFoundException bnf) {
   * throw new Exception("Reserva no encontrada");
   * } catch (UserNotFoundException unf) {
   * throw new Exception("Usuario no encontrado");
   * }
   * }
   */

   private int getAuthenticatedUserId() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    Object principal = authentication.getPrincipal();
  
    if (principal instanceof User user) {
      return user.getId();
    }
  
    throw new SecurityException("Usuario no autenticado correctamente");
  }  

  private PageRequest buildPageRequest(int page, String sortBy, boolean ascending) {
    Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    return PageRequest.of(page, 6, sort);
  }
}
