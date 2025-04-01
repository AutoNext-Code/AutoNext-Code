package com.autonext.code.autonext_server.repositories;

import com.autonext.code.autonext_server.models.Booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>, JpaSpecificationExecutor<Booking>  {

  Page<Booking> findByUserId(int userId, Pageable pageable);

}
