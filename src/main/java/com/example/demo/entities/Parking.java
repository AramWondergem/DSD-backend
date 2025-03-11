package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "parkings")
public class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numberOfGuestSpots;

    private Integer numberOfTenantSpots;

    @OneToOne(mappedBy = "parking")
    Door door;

    @ManyToOne
    @JoinColumn(name = "building_id")
    Building building;
}
