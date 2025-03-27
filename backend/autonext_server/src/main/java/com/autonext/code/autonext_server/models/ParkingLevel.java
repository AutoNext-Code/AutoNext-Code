package com.autonext.code.autonext_server.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "parking_level")
public class ParkingLevel {
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    public int id ; 

    @Column(unique = true, nullable = false)
    public String name ;

}
