package com.autonext.code.autonext_server.specifications;

import com.autonext.code.autonext_server.models.enums.PlugType;
import org.springframework.data.jpa.domain.Specification;
import com.autonext.code.autonext_server.models.ParkingSpace;

public class ParkingSpaceSpecifications {

  public static Specification<ParkingSpace> hasLevel(Integer levelId) {
    return (root, query, cb) -> {
      if (levelId == null)
        return null;
      return cb.equal(root.get("parkingLevel").get("id"), levelId);
    };
  }

  public static Specification<ParkingSpace> isElectric() {
    return (root, query, cb) -> cb.notEqual(root.get("plugType"), PlugType.NoType);
  }
}
