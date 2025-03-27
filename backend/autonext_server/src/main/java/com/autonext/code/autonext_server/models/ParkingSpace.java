package com.autonext.code.autonext_server.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "parking_space")
public class ParkingSpace {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    public int id ; 

    @ManyToOne
    @JoinColumn(name = "parking_level_id", nullable = false)
    public ParkingLevel parkingLevel ;

    @Column(unique = true, nullable = false)
    public String spaceCode ;

    @Column()
    public Booking[] booking ;

    @Column(nullable = false)
    public PlugType plugType ;

    @Column(nullable = false)
    public ParkingState Availability ;

}
