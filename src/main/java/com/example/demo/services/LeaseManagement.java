package com.example.demo.services;

import com.dropbox.sign.ApiException;
import com.dropbox.sign.model.SignatureRequestGetResponse;
import com.example.demo.dto.LeaseDTO;
import com.example.demo.dto.LeaseSignRequestDTO;

public interface LeaseManagement {
    SignatureRequestGetResponse createLeaseSignatureRequest(LeaseSignRequestDTO leaseSignRequestDTO) throws ApiException;

    void cancelLeaseSignatureRequest(Long externalId) throws ApiException;

    LeaseDTO getLeaseStatus(Long externalId) throws ApiException;
}
