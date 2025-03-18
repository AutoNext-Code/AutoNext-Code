package com.autonext.code.autonext_server.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="slots")
public class ParkingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String PlugType;

    @Column(nullable = false)
    private int parkingNumber;

    @Column(nullable = false)
    private int availability;

    @Column(nullable = false)
    private int position_x;

    @Column(nullable = false)
    private int position_y;

    @Column(nullable = false)
    private int position_direction;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private ParkingLevel parkingLevel;
    

    public ParkingSlot(String PlugType, int parkingNumber, int availability, int[] position, ParkingLevel parkingLevel){
        this.PlugType = PlugType;
        this.parkingNumber = parkingNumber;
        this.availability = availability;
        this.position_x = position[0];
        this.position_y = position[1];
        this.position_direction = position[2];
        this.parkingLevel = parkingLevel;
    }

    public int getId() {
        return id;
    }

    public String getPlugType() {
        return PlugType;
    }

    public int getParkingNumber() {
        return parkingNumber;
    }

    public int getAvailability() {
        return availability;
    }

    public int getPosition_x() {
        return position_x;
    }

    public int getPosition_y() {
        return position_y;
    }

    public int getPosition_direction() {
        return position_direction;
    }

    
    
    
}
