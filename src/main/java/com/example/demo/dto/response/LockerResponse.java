package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LockerResponse {

    Long id;

    Integer lockerNumber;

    Long apartmentNumber;

//    @JsonFormat(pattern = "YYYY-MM-dd'T'HH:mm:ss.SSSZ")
//    ZonedDateTime creationDate;

}
