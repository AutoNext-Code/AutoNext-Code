package com.autonext.code.autonext_server.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.ArrayList;
import java.util.List;

import com.autonext.code.autonext_server.models.enums.Direction;
import com.autonext.code.autonext_server.models.enums.PlugType;

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

    @Column (nullable = false)
    public String name ;

    @Column( nullable = false)
    public int x ;

    @Column( nullable = false)
    public int y ;

    @Column( nullable = false)
    public Direction direction ;

    @Column(nullable = false)
    public PlugType plugType ;

    @OneToMany(mappedBy = "parkingSpace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parking_level_id", nullable = false)
    public ParkingLevel parkingLevel ;

    public ParkingSpace() {
    }

    public ParkingSpace(String name, int x, int y, Direction direction, PlugType plugType, ParkingLevel parkingLevel) {
        this.name = name ;
        this.x = x ;
        this.y = y ;
        this.direction = direction ;
        this.plugType = plugType ;
        this.parkingLevel = parkingLevel;
    }

    public String getName() {
        return name;
    }  

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public PlugType getPlugType() {
        return plugType;
    }

    public void setPlugType(PlugType plugType) {
        this.plugType = plugType;
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

    public String getSpaceCode() {
        String position = this.x +""+ this.y+""+this.direction;

        return position;
        
    }

}
