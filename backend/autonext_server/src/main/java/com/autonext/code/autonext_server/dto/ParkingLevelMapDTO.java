package com.autonext.code.autonext_server.dto;

import com.autonext.code.autonext_server.models.ParkingLevel;

import java.util.List;

public class ParkingLevelMapDTO {
  private int id;
  private String name;
  private String imageUrl;
  private String workCenterName;
  private List<Space> spaces;

  public ParkingLevelMapDTO(ParkingLevel level, String baseImageUrl, List<Space> spaces) {
    this.id = level.getId();
    this.name = level.getName();
    this.imageUrl = baseImageUrl + level.getImageName();
    this.workCenterName = level.getWorkCenter().getName();
    this.spaces = spaces;
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
    private int x;
    private int y;
    private String direction;
    private String plugType;
    private String state;

    public Space(int id, int x, int y, String direction, String plugType, String state) {
      this.id = id;
      this.x = x;
      this.y = y;
      this.direction = direction;
      this.plugType = plugType;
      this.state = state;
    }

    public int getId() {
      return id;
    }

    public Integer getX() {
      return x;
    }

    public Integer getY() {
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
