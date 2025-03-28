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
@Table(name = "parking_level")
public class ParkingLevel {
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    public int id ; 

    @Column(unique = true, nullable = false)
    public String name ;

    @ManyToOne
    @JoinColumn(name = "parking_space_id", nullable = false)
    public ParkingSpace parkingSpace ;

    @ManyToOne
    @JoinColumn(name = "work_center_id", nullable = false)
    public WorkCenter workCenter ;

    public ParkingLevel(String name) {
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

    public ParkingSpace getParkingSpace() {
        return parkingSpace;
    }

    public void setParkingSpace(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    public WorkCenter getWorkCenter() {
        return workCenter;
    }

    public void setWorkCenter(WorkCenter workCenter) {
        this.workCenter = workCenter;
    }

    //TODO:CORREGIR PARKING LEVEL REPOSITORY Y TERMINAR LOS REPOSITORIOS ANTES DE PASAR A LOS CONTROLADORES

    // HOY HE EMPEZADO LOS REPOSITORIOS, Y HE CREADO LOS MODELOS. ADEMAS, HEMOS PLANEADO EL SPRINT 2 

}
