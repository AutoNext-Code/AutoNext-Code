package com.autonext.code.autonext_server.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.autonext.code.autonext_server.models.enums.BookingStatus;
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
      @RequestParam(required = false, name = "delegation") String workCenter,
      @RequestParam(required = false) String carPlate) {

    int userId = getAuthenticatedUserId();
    PageRequest pageable = buildPageRequest(page, sortBy, ascending);

    Page<Booking> bookings = bookingService.getFilteredBookingsPaged(
        userId, pageable, date, workCenter, carPlate);

    List<BookingDTO> bookingDTOs = bookings.getContent().stream()
        .map(BookingMapper::toDTO)
        .toList();

    return new PageImpl<>(bookingDTOs, pageable, bookings.getTotalElements());
  }


  @PostMapping("")
  public ResponseEntity<String> createBooking(@Valid @RequestBody MapBookingDTO booking) {
	  
	try {

		int userId = getAuthenticatedUserId() ;
    
    System.out.println("");
    System.out.println("");
    System.out.println("");
    System.out.println("");
    System.out.println("Aqui estamos");
    System.out.println(booking.getCarId());
    System.out.println(booking.getParkingSpaceId());
    System.out.println(booking.getStartTime());
    System.out.println(booking.getEndTime());
    System.out.println("");
    System.out.println("");
    System.out.println("");
    System.out.println("");

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
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateBooking(@RequestParam int id, @RequestParam BookingStatus bookingStatus) throws Exception {
	try {
		int userId = getAuthenticatedUserId() ;
		bookingService.updateBookingState(id, userId, bookingStatus);
		return ResponseEntity.ok("Reserva modificada correctamente");
	} catch (BookingNotFoundException bnf) {
        throw new Exception("Reserva no encontrada");
	} catch (UserNotFoundException unf) {
        throw new Exception("Usuario no encontrado");
	}
  }

  private int getAuthenticatedUserId() {
    UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder
        .getContext().getAuthentication();
    User user = (User) authentication.getPrincipal();
    return user.getId();
  }

  private PageRequest buildPageRequest(int page, String sortBy, boolean ascending) {
    Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    return PageRequest.of(page, 6, sort);
  }

}
