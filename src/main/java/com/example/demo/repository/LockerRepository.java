package com.example.demo.repository;

import com.example.demo.entities.Apartment;
import com.example.demo.entities.Locker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LockerRepository extends JpaRepository<Locker, Long> {
    List<Locker> findAllByBuilding_Id(Long buildingId);

    List<Locker> findAllByApartment(Apartment apartment);
}
