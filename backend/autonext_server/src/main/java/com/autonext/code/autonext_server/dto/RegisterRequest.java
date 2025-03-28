package com.autonext.code.autonext_server.dto;

public class RegisterRequest {
    private String email;
    private String name;
    private String surname;
    private String password;
    private String carPlate;


    public RegisterRequest() {}

    public RegisterRequest(String email, String name, String surname, String password, String carPlate) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.carPlate = carPlate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }
}
