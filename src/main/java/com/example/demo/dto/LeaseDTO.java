package com.example.demo.dto;

import com.dropbox.sign.model.SignatureRequestGetResponse;
import com.example.demo.entities.Apartment;
import com.example.demo.entities.Tenant;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class LeaseDTO {
    Long id;
    String status;
    String externalId;
    String startDate;
    String endDate;
    Apartment apartment;
    List<Tenant> tenants;
    String dropboxUrl;
    SignatureRequestGetResponse signatureRequestGetResponse;
}
