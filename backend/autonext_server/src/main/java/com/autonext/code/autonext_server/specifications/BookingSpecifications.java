package com.autonext.code.autonext_server.specifications;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.autonext.code.autonext_server.models.Booking;

public class BookingSpecifications {

  public static Specification<Booking> hasUserId(int userId) {
    return (root, query, cb) -> cb.equal(root.get("user").get("id"), userId);
  }

  public static Specification<Booking> hasDate(LocalDate date) {
    return (root, query, cb) -> date == null ? null : cb.equal(root.get("date"), date);
  }

  public static Specification<Booking> hasWorkCenterId(Integer workCenterId) {
    return (root, query, cb) -> {
      if (workCenterId == null)
        return null;
      return cb.equal(root.get("workCenter").get("id"), workCenterId);
    };
  }

  public static Specification<Booking> hasCarId(Integer carId) {
    return (root, query, cb) -> {
      if (carId == null)
        return null;
      return cb.equal(root.get("car").get("id"), carId);
    };
  }
}
