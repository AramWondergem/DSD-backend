package com.example.demo.dto;

import com.example.demo.entities.Apartment;
import com.example.demo.entities.Tenant;
import lombok.Data;

import java.util.List;

@Data
public class LeaseDTO {
    String status;
    String externalId;
    String startDate;
    String endDate;
    Apartment apartment;
    List<Tenant> tenants;
}
