package com.example.demo.mappers;

import com.example.demo.dto.LeaseDTO;
import com.example.demo.dto.LeaseSignRequestDTO;
import com.example.demo.entities.Lease;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LeaseMapper {

    LeaseSignRequestDTO toLeaseRequestDto(Lease lease);

     Lease toEntity(LeaseDTO dto);

    LeaseDTO toDto(Lease lease);
}