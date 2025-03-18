package com.example.demo.mappers;

import com.example.demo.dto.response.LockerResponse;
import com.example.demo.entities.Locker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Map;


@Mapper(componentModel = "spring")
public interface LockerMapper {

    @Mapping(target = "apartmentNumber", source = "locker.apartment.apartmentNumber")
    LockerResponse map(Locker locker);

    List<LockerResponse> map(List<Locker> list);

    Map<Long, List<LockerResponse>> map(Map<Long,List<Locker>> map);
}
