package com.autonext.code.autonext_server.models;

import java.time.LocalDate;
import java.time.LocalTime;

import com.autonext.code.autonext_server.models.enums.StrikeReason;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "strikes")
public class Strike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalTime time;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private StrikeReason reason;

    @Column(nullable= false)
    private boolean active;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public Strike (){

    }

    public Strike(LocalDate date, LocalTime time, StrikeReason reason, User user){
        this.time=time;
        this.date=date;
        this.reason=reason;
        this.active= true;
    }

    public int getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }

    public LocalDate getDate() {
        return date;
    }

    public StrikeReason getReason() {
        return reason;
    }

    public User getUser() {
        return user;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    
    
    
    public boolean isActive() {
        return active;
    }
}
