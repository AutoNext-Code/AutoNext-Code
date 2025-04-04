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
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String carPlate;

    @Column
    private String name;

    @Column
    private PlugType plugType;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Car() {
        this.carPlate = "Undefined";
        this.name = "Coche 1";
        this.plugType = PlugType.Undefined;
    }

    public Car(String carPlate, User user) {
        super();
        this.carPlate = carPlate;
        this.user = user;
    }

    public Car(String carPlate, String name, PlugType plugType, User user) {
        this.carPlate = carPlate;
        this.name = name;
        this.plugType = plugType;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlugType getPlugType() {
        return plugType;
    }

    public void setPlugType(PlugType plugType) {
        this.plugType = plugType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}