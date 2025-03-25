package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "apartments")
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "apartment_number", unique = true)
    Long apartmentNumber;

    @OneToMany(mappedBy = "apartment")
    Set<Door> doors;

    @ManyToOne
    @JoinColumn(name = "building_id", nullable = false)
    Building building;

    @OneToMany(mappedBy = "apartment")
    List<Lease> leaseHistory;

    @ManyToMany(mappedBy = "apartments")
    Set<User> tenants;

    @OneToMany(mappedBy = "apartment")
    List<Locker> lockers;
}
