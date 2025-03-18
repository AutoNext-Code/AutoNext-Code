package com.autonext.code.autonext_server.models;


import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="levels")
public class ParkingLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int level;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private WorkCenter workCenter;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="parkingLevel")
    private Set<ParkingSlot> slots;

    public ParkingLevel(int level, WorkCenter workCenter){
        this.level = level;
        this.workCenter= workCenter;
    }

    public int getLevel() {
        return level;
    }


}
