package com.autonext.code.autonext_server.dto;

import com.autonext.code.autonext_server.models.enums.Direction;
import com.autonext.code.autonext_server.models.enums.ParkingState;
import com.autonext.code.autonext_server.models.enums.PlugType;

public class ParkingSpaceDTO {
  private int id;
  private String name;
  private int x;
  private int y;
  private Direction direction;
  private PlugType plugType;
  private int plugTypeId; 
  private Integer floorId; 
  private String delegation;
  private ParkingState status;

  // 🧱 Getters y Setters
  public int getId() { return id; }
  public void setId(int id) { this.id = id; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public int getX() { return x; }
  public void setX(int x) { this.x = x; }

  public int getY() { return y; }
  public void setY(int y) { this.y = y; }

  public Direction getDirection() { return direction; }
  public void setDirection(Direction direction) { this.direction = direction; }

  public PlugType getPlugType() { return plugType; }
  public void setPlugType(PlugType plugType) { this.plugType = plugType; }

  public int getPlugTypeId() { return plugTypeId; } 
  public void setPlugTypeId(int plugTypeId) { this.plugTypeId = plugTypeId; } 

  public Integer getFloorId() { return floorId; } 
  public void setFloorId(Integer floorId) { this.floorId = floorId; } 

  public String getDelegation() { return delegation; }
  public void setDelegation(String delegation) { this.delegation = delegation; }

  public ParkingState getStatus() { return status; }
  public void setStatus(ParkingState status) { this.status = status; }

  public ParkingSpaceDTO(int id, String name, int x, int y, Direction direction,
                         PlugType plugType, int plugTypeId, Integer floorId, String delegation, ParkingState status) {
      this.id = id;
      this.name = name;
      this.x = x;
      this.y = y;
      this.direction = direction;
      this.plugType = plugType;
      this.plugTypeId = plugTypeId;
      this.floorId = floorId; 
      this.delegation = delegation;
      this.status = status;
  }
}

