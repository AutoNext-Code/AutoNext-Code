package com.autonext.code.autonext_server.repositories;

import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.ParkingSpace;
import com.autonext.code.autonext_server.models.enums.BookingStatus;
import com.autonext.code.autonext_server.models.enums.ConfirmationStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>, JpaSpecificationExecutor<Booking> {

        @Query("SELECT b FROM Booking b WHERE b.status = :status AND b.date = :date AND b.startTime BETWEEN :startTime AND :endTime")
        List<Booking> findReservationsToStartSoon(@Param("status") BookingStatus status, @Param("date") LocalDate date,
                        @Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime);

        @Query("SELECT b FROM Booking b WHERE b.status = :status AND b.date = :date AND b.endTime BETWEEN :startTime AND :endTime")
        List<Booking> findReservationsToEndSoon(@Param("status") BookingStatus status, @Param("date") LocalDate date,
                        @Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime);

        @Query("SELECT b FROM Booking b WHERE b.status = :status AND b.date = :date AND b.endTime <= :currentTime")
        List<Booking> findCompletedBookings(@Param("status") BookingStatus status, @Param("date") LocalDate date,
                        @Param("currentTime") LocalTime currentTime);

        @Query("SELECT b FROM Booking b WHERE b.date = :date AND b.parkingSpace = :space")
        List<Booking> findAllReservationsByDateAndSpace(@Param("date") LocalDate date,
                        @Param("space") ParkingSpace space);

        @Query("SELECT b FROM Booking b WHERE b.confirmationStatus = :confirmationStatus AND b.date = :date AND b.startTime <= :expiredTime")
        List<Booking> findExpiredPendingConfirmations(
                        @Param("confirmationStatus") ConfirmationStatus confirmationStatus,
                        @Param("date") LocalDate date,
                        @Param("expiredTime") LocalTime expiredTime);

}
