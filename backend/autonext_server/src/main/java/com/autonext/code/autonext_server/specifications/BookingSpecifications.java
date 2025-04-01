package com.autonext.code.autonext_server.specifications;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.enums.BookingStatus;
import com.autonext.code.autonext_server.models.enums.PlugType;

public class BookingSpecifications {

  public static Specification<Booking> hasUserId(int userId) {
    return (root, query, cb) -> cb.equal(root.get("user").get("id"), userId);
  }

  public static Specification<Booking> hasStartTime(String startTime) {
    return (root, query, cb) -> {
      if (startTime == null)
        return null;
      return cb.equal(root.get("startTime"), java.time.LocalTime.parse(startTime));
    };
  }

  public static Specification<Booking> hasEndTime(String endTime) {
    return (root, query, cb) -> {
      if (endTime == null)
        return null;
      return cb.equal(root.get("endTime"), java.time.LocalTime.parse(endTime));
    };
  }

  public static Specification<Booking> hasStatus(String status) {
    return (root, query, cb) -> {
      if (status == null)
        return null;
      return cb.equal(root.get("status"), BookingStatus.valueOf(status));
    };
  }

  public static Specification<Booking> hasDate(LocalDate date) {
    return (root, query, cb) -> date == null ? null : cb.equal(root.get("date"), date);
  }

  public static Specification<Booking> hasDelegation(String delegation) {
    return (root, query, cb) -> {
      if (delegation == null)
        return null;
      return cb.equal(root.get("workCenter").get("name"), delegation);
    };
  }

  public static Specification<Booking> hasCarPlate(String carPlate) {
    return (root, query, cb) -> {
      if (carPlate == null)
        return null;
      return cb.equal(root.get("car").get("carPlate"), carPlate);
    };
  }

  public static Specification<Booking> hasPlugType(String plugType) {
    return (root, query, cb) -> {
      if (plugType == null)
        return null;
      return cb.equal(root.get("car").get("plugType"), PlugType.valueOf(plugType));
    };
  }

  public static Specification<Booking> hasFloor(String floor) {
    return (root, query, cb) -> {
      if (floor == null)
        return null;
      return cb.equal(root.get("parkingSpace").get("parkingLevel").get("floor"), floor);
    };
  }

}
