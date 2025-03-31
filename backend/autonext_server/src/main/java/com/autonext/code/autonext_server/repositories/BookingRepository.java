package com.autonext.code.autonext_server.repositories;

import com.autonext.code.autonext_server.models.Booking;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

  Page<Booking> findByUserId(int userId, Pageable pageable);

  @Query("SELECT b FROM Booking b WHERE b.user.id = :userId " +
      "AND (:date IS NULL OR b.date = :date) " +
      "AND (:workCenter IS NULL OR b.workCenter.name = :workCenter) " +
      "AND (:carPlate IS NULL OR b.car.carPlate = :carPlate)")
  Page<Booking> findByFilters(
      @Param("userId") int userId,
      @Param("date") LocalDate date,
      @Param("workCenter") String workCenter,
      @Param("carPlate") String carPlate,
      Pageable pageable);

}
