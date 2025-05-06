package com.autonext.code.autonext_server.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.List;

import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;

@Entity
@Table(name = "work_center")
public class WorkCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id ; 

    @Column(unique = true, nullable = false)
    public String name ;

    @OneToMany(mappedBy = "workCenter", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<ParkingLevel> parkingLevels;

    public WorkCenter() {
        
    }

    public WorkCenter(String name) {
        this.name = name ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ParkingLevel> getParkingLevels() {
        return parkingLevels;
    }

    public void setParkingLevels(List<ParkingLevel> parkingLevels) {
        this.parkingLevels = parkingLevels;
    }
    
}