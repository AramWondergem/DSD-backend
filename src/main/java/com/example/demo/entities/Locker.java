package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "lockers")
public class Locker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Integer lockerNumber;

    @ManyToOne
    @JoinColumn(name = "building_id",nullable = false)
    Building building;


    @ManyToOne
    @JoinColumn(name = "apartment_id")
    Apartment apartment;

    ZonedDateTime updateDate;

    @PreUpdate
    public void setCreationDate() {
        this.updateDate = ZonedDateTime.now();
    }
}
