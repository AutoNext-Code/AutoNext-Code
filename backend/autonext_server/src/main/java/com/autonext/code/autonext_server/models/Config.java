package com.autonext.code.autonext_server.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "config")
public class Config {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "daily_limit", nullable = false)
    private int dailyLimit;

    @Column(name = "lead_time", nullable = false)
    private int leadTime;

    public Config() {

    }
    
    public Config(int dailyLimit, int leadTime) {
        this.dailyLimit = dailyLimit;
        this.leadTime = leadTime;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(int dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public int getAdvanceMinutes() {
        return leadTime;
    }

    public void setAdvanceMinutes(int leadTime) {
        this.leadTime = leadTime;
    }


    
}
