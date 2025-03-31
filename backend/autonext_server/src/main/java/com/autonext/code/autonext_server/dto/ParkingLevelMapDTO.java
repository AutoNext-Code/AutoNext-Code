package com.autonext.code.autonext_server.dto;

import com.autonext.code.autonext_server.models.ParkingSpace;
import com.autonext.code.autonext_server.models.ParkingLevel;

import java.util.stream.Collectors;
import java.util.List;

public class ParkingLevelMapDTO {
  private int id;
  private String name;
  private String imageUrl;
  private String workCenterName;
  private List<Space> spaces;

  public ParkingLevelMapDTO(ParkingLevel level, String baseImageUrl) {
    this.id = level.getId();
    this.name = level.getName();
    this.imageUrl = baseImageUrl + level.getImageName();
    this.workCenterName = level.getWorkCenter().getName();
    this.spaces = level.getParkingSpaces().stream()
        .map(Space::new)
        .collect(Collectors.toList());
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public String getWorkCenterName() {
    return workCenterName;
  }

  public List<Space> getSpaces() {
    return spaces;
  }

  public static class Space {
    private int id;
    private String x;
    private String y;
    private String direction;
    private String plugType;
    private String state;

    public Space(ParkingSpace space) {
      this.id = space.getId();
      this.x = space.getX();
      this.y = space.getY();
      this.direction = space.getDirection().name();
      this.plugType = space.getPlugType().name();
      this.state = space.getState().name();
    }

    public int getId() {
      return id;
    }

    public String getX() {
      return x;
    }

    public String getY() {
      return y;
    }

    public String getDirection() {
      return direction;
    }

    public String getPlugType() {
      return plugType;
    }

    public String getState() {
      return state;
    }
  }
}
