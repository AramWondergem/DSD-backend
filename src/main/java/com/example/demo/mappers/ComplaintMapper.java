package com.example.demo.mappers;

import com.example.demo.dto.response.ComplaintResponse;
import com.example.demo.entities.Complaint;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface ComplaintMapper {

    ComplaintResponse mapToResponse(Complaint complaint);
}
