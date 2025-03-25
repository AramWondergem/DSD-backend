package com.example.demo.dto.response;

import com.example.demo.dto.UserDTO;
import com.example.demo.util.enums.ComplaintStatus;
import com.example.demo.util.enums.ComplaintType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplaintResponse {

    Long id;

    UserDTO user;

    String message;

    @JsonFormat(pattern = "YYYY-MM-dd'T'HH:mm:ss.SSSZ")
    ZonedDateTime timeCreated;

    ComplaintStatus complaintStatus;

    ComplaintType complaintType;
}
