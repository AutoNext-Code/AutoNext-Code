package com.autonext.code.autonext_server.repositories;

import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
  
  Optional<Booking> findByUser(User user);

  // List<Booking> findByDate(LocalDate date, Pageable pageable);

  
}
