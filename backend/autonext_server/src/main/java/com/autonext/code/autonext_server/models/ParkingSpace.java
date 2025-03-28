package com.autonext.code.autonext_server.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "parking_space")
public class ParkingSpace {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    public int id ;

    @Column(unique = true, nullable = false)
    public String spaceCode ;

    @Column()
    public Booking[] booking ;

    @Column(nullable = false)
    public PlugType plugType ;

    @Column(nullable = false)
    public ParkingState state ;

    @ManyToOne
    @JoinColumn(name = "parking_level_id", nullable = false)
    public ParkingLevel parkingLevel ;

    public ParkingSpace(String spaceCode, PlugType plugType, ParkingState state) {

        this.spaceCode = spaceCode ;
        this.plugType = plugType ;
        this.state = state ;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpaceCode() {
        return spaceCode;
    }

    public void setSpaceCode(String spaceCode) {
        this.spaceCode = spaceCode;
    }

    public Booking[] getBooking() {
        return booking;
    }

    public void setBooking(Booking[] booking) {
        this.booking = booking;
    }

    public PlugType getPlugType() {
        return plugType;
    }

    public void setPlugType(PlugType plugType) {
        this.plugType = plugType;
    }

    public ParkingState getState() {
        return state;
    }

    public void setState(ParkingState state) {
        this.state = state;
    }

    public ParkingLevel getParkingLevel() {
        return parkingLevel;
    }

    public void setParkingLevel(ParkingLevel parkingLevel) {
        this.parkingLevel = parkingLevel;
    }

}
