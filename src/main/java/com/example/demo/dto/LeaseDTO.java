package com.example.demo.dto;

import com.dropbox.sign.model.SignatureRequestGetResponse;
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
    Long apartmentNumber;
    List<TenantDTO> tenants;
    String dropboxUrl;
    SignatureRequestGetResponse signatureRequestGetResponse;
}
