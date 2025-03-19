package com.example.demo.services;

import com.example.demo.dto.request.LockerRequest;
import com.example.demo.entities.Apartment;
import com.example.demo.entities.Locker;
import com.example.demo.entities.User;
import com.example.demo.repository.ApartmentRepository;
import com.example.demo.repository.LockerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LockerServiceImpl implements LockerService {

    private final UserService userService;
    private final LockerRepository lockerRepository;
    private final ApartmentRepository apartmentRepository;

    @Override
    public Locker get(Long id) {
        return lockerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Locker is not found"));
    }

    @Override
    public Locker update(Long lockerId, LockerRequest lockerRequest) {
        Locker locker = get(lockerId);

        Apartment apartment = apartmentRepository.findByApartmentNumber(lockerRequest.getApartmentNumber()).orElse(null);

        locker.setApartment(apartment);

        return lockerRepository.save(locker);
    }

    @Override
    public Map<Long,List<Locker>> getAllByUserId(Long id) {
        User user = userService.getUser(id);

        Map<Long, List<Locker>> lockersPerApartment = new HashMap<>();

        for(Apartment apartment : user.getApartments()) {
            List<Locker> lockers = lockerRepository.findAllByApartment(apartment);
            lockersPerApartment.put(apartment.getApartmentNumber(), lockers);
        }

        return lockersPerApartment;
    }

    @Override
    public List<Locker> getAllByBuildingId(Long id) {
        return lockerRepository.findAllByBuilding_Id(id);
    }
}
