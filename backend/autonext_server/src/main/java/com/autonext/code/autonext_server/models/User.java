package com.autonext.code.autonext_server.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;


    @Column(nullable = false)
    private String password;

    // private List<Car> cars; TODO:

    // private List<Booking> booking; TODO:

    // private WorkCenter workCenter; TODO:

    private String jobPosition;

    @Column(nullable = false)
    private Role rol;

    @Column(nullable = false)
    private boolean isBanned;
    
    @Column(nullable = false)
    private boolean emailConfirm; 

    private String confirmationToken;

    public User() {
        this.rol = Role.User;
        this.isBanned = false;
        this.emailConfirm = false;
        this.jobPosition = "";
        this.confirmationToken = "";
    }

    public User(String email, String name, String surname, String password) {
        super();
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
    }

    


    
}
