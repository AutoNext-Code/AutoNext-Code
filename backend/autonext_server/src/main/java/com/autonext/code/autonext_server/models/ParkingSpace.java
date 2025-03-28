package com.autonext.code.autonext_server.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.List;

import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@Table(name = "parking_space")
public class ParkingSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id ;

    @Column( nullable = false)
    public String x ;

    @Column( nullable = false)
    public String y ;

    @Column( nullable = false)
    public String direction ;

    @Column(nullable = false)
    public PlugType plugType ;

    @Column(nullable = false)
    public ParkingState state ;

    @OneToMany(mappedBy = "parkingSpace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;

    @ManyToOne
    @JoinColumn(name = "parking_level_id", nullable = false)
    public ParkingLevel parkingLevel ;

    public ParkingSpace(String x, String y, String direction, PlugType plugType, ParkingState state) {
        this.x = x ;
        this.y = y ;
        this.direction = direction ;
        this.plugType = plugType ;
        this.state = state ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
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

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public ParkingLevel getParkingLevel() {
        return parkingLevel;
    }

    public void setParkingLevel(ParkingLevel parkingLevel) {
        this.parkingLevel = parkingLevel;
    }

}
