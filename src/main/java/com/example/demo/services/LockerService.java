package com.example.demo.services;

import com.example.demo.dto.request.LockerRequest;
import com.example.demo.entities.Locker;

import java.util.List;
import java.util.Map;

public interface LockerService {

    Locker get(Long id);
    Locker update(Long lockerId, LockerRequest lockerRequest);

    Map<Long,List<Locker>> getAllByUserId(Long id);
    List<Locker> getAllByBuildingId(Long id);
}
