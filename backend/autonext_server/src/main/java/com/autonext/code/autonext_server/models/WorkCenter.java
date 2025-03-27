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
@Table(name = "work_center")
public class WorkCenter {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    public int id ; 

    @Column(unique = true, nullable = false)
    public String name ;

    @Column(unique = true)
    public String address ;

    @ManyToOne
    @JoinColumn(name = "parking_level_id", nullable = false)
    public ParkingLevel parkingLevel ;
    
}