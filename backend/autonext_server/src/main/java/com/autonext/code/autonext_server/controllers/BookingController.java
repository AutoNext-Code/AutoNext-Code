package com.autonext.code.autonext_server.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.autonext.code.autonext_server.dto.BookingDTO;
import com.autonext.code.autonext_server.mapper.BookingMapper;
import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.services.BookingService;
import com.autonext.code.autonext_server.services.JwtService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

  private final BookingService bookingService;
  private final JwtService jwtService;

  public BookingController(BookingService bookingService, JwtService jwtService) {
    this.bookingService = bookingService;
    this.jwtService = jwtService;
  }

  @GetMapping("/user")
  @SecurityRequirement(name = "bearerAuth")
  public Page<BookingDTO> getBookingsByUser(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "date") String sortBy,
      @RequestParam(defaultValue = "true") boolean ascending,
      @RequestParam(required = false) LocalDate date,
      @RequestParam(required = false, name = "delegation") String workCenter,
      @RequestParam(required = false) String carPlate,
      @RequestParam(required = false) String plugType,
      @RequestParam(required = false) String floor,
      @RequestParam(required = false) String startTime,
      @RequestParam(required = false) String endTime) {

    int userId = getAuthenticatedUserId();
    PageRequest pageable = buildPageRequest(page, sortBy, ascending);

    Page<Booking> bookings = bookingService.getFilteredBookingsPaged(
        userId, pageable, date, workCenter, carPlate, plugType, floor, startTime, endTime);

    List<BookingDTO> bookingDTOs = bookings.getContent().stream()
        .map(BookingMapper::toDTO)
        .toList();

    return new PageImpl<>(bookingDTOs, pageable, bookings.getTotalElements());
  }

  @GetMapping("/map")
  @SecurityRequirement(name = "bearerAuth")
  public List<BookingDTO> getBookingsForMap(
      @RequestParam(required = false) LocalDate date,
      @RequestParam(required = false, name = "delegation") String workCenter,
      @RequestParam(required = false) String carPlate,
      @RequestParam(required = false) String plugType,
      @RequestParam(required = false) String floor,
      @RequestParam(required = false) String startTime,
      @RequestParam(required = false) String endTime) {

    int userId = getAuthenticatedUserId();

    List<Booking> bookings = bookingService.getAllFilteredBookings(
        userId, date, workCenter, carPlate, plugType, floor, startTime, endTime);

    return bookings.stream().map(BookingMapper::toDTO).toList();
  }

  @PostMapping
  public BookingDTO createBooking(@RequestParam Booking booking) {
    Booking createdBooking = bookingService.createBooking(booking);
    return BookingMapper.toDTO(createdBooking);
  }

  @PutMapping("/{id}")
  public BookingDTO updateBooking(@RequestParam int id, @RequestParam Booking booking) {
    Booking updatedBooking = bookingService.updateBooking(id, booking);
    return BookingMapper.toDTO(updatedBooking);
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
