package com.example.demo.services;

import com.dropbox.sign.ApiClient;
import com.dropbox.sign.ApiException;
import com.dropbox.sign.Configuration;
import com.dropbox.sign.api.SignatureRequestApi;
import com.dropbox.sign.model.*;
import com.example.demo.dto.LeaseDTO;
import com.example.demo.dto.LeaseSignRequestDTO;
import com.example.demo.entities.*;
import com.example.demo.repository.ApartmentRepository;
import com.example.demo.repository.LeaseRepository;
import com.example.demo.repository.TenantRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.enums.DocStatus;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LeaseManagementDropBoxImpl implements LeaseManagement {
    private SignatureRequestApi signatureRequestApi;
    private final UserRepository userRespository;
    private final LeaseRepository leaseRepository;
    private final ApartmentRepository apartmentRepository;
    private final TenantRepository tenantRepository;
    @Value("${dropBoxSignToken}")
    String dropBoxSignToken;

    @Autowired
    public LeaseManagementDropBoxImpl(UserRepository userRepository, LeaseRepository leaseRepository, ApartmentRepository apartmentRepository, TenantRepository tenantRepository) {
        this.userRespository = userRepository;
        this.leaseRepository = leaseRepository;
        this.apartmentRepository = apartmentRepository;
        this.tenantRepository = tenantRepository;
    }

    @PostConstruct
    private void initApiClient() {
        ApiClient apiClient = Configuration.getDefaultApiClient().setApiKey(dropBoxSignToken);
        this.signatureRequestApi = new SignatureRequestApi(apiClient);
    }


    @Transactional(rollbackFor = Exception.class)
    public SignatureRequestGetResponse createLeaseSignatureRequest(LeaseSignRequestDTO leaseSignRequestDTO) throws ApiException {
        SignatureRequestGetResponse response;
        Optional<User> userRecord = userRespository.findByEmailIgnoreCase(leaseSignRequestDTO.getSignerEmails().getFirst());
        User user = userRecord.orElseThrow(() -> new EmptyResultDataAccessException("user not found", 1));
        var signer = new SubSignatureRequestSigner().emailAddress(leaseSignRequestDTO.getSignerEmails().getFirst()).name(user.getName()).order(0);
        var signOptions = new SubSigningOptions().draw(true).type(true).defaultType(SubSigningOptions.DefaultTypeEnum.DRAW);
        var subFieldOptions = new SubFieldOptions().dateFormat(SubFieldOptions.DateFormatEnum.DDMMYYYY);
        var data = new SignatureRequestSendRequest()
                .title(leaseSignRequestDTO.getMetaData().getTitle())
                .subject("Lease Agreement with Super")
                .message("please review and sign the lease")
                .signers(List.of(signer))
                .ccEmailAddresses(leaseSignRequestDTO.getCcEmails())
                .addFilesItem(leaseSignRequestDTO.getFile())
                .signingOptions(signOptions)
                .fieldOptions(subFieldOptions)
                .testMode(true);
        Optional<Apartment> apartmentOptional = apartmentRepository.findByApartmentNumber(leaseSignRequestDTO.getApartmentNumber());
        Apartment apartment = apartmentOptional.orElseThrow(() -> new EmptyResultDataAccessException("no record matches apartment number in database", 1));
        Optional<Tenant> tenantOptional = Optional.of(tenantRepository.findByUser(user).orElse(tenantRepository.save(Tenant.builder().user(user).build())));
        response = signatureRequestApi.signatureRequestSend(data);
        Lease newLease = leaseRepository.save(Lease.builder().status(DocStatus.PENDING).apartment((apartment)).externalId(response.getSignatureRequest().getSignatureRequestId())
                .startDate(parseZonedDateTime(leaseSignRequestDTO.getMetaData().getStartDate()))
                .endDate(parseZonedDateTime(leaseSignRequestDTO.getMetaData().getEndDate()))
                .tenants(List.of(tenantOptional.get())).dropboxDocumentUrl(response.getSignatureRequest().getFilesUrl())
                .build());
        log.info("new lease created");
        return response;
    }


    public void cancelLeaseSignatureRequest(Long leaseId) throws ApiException, EmptyResultDataAccessException {
        Lease lease = leaseRepository.findById(leaseId).orElseThrow();
        signatureRequestApi.signatureRequestCancel(lease.getExternalId());
        lease.setStatus(DocStatus.CANCELED);
        leaseRepository.save(lease);
    }


    public LeaseDTO getLeaseStatus(Long leaseId) throws ApiException, EmptyResultDataAccessException {
        SignatureRequestGetResponse result = null;
        Lease lease = leaseRepository.findById(leaseId).orElseThrow();
        if(lease.getStatus() == DocStatus.PENDING){
             result = signatureRequestApi.signatureRequestGet(lease.getExternalId());
             lease.setDropboxDocumentUrl(result.getSignatureRequest().getFilesUrl());
        if (Boolean.TRUE.equals(result.getSignatureRequest().getIsComplete())) {
            lease.setStatus(DocStatus.SIGNED);
        }else if(Boolean.TRUE.equals(result.getSignatureRequest().getIsDeclined())){
            lease.setStatus(DocStatus.CANCELED);
        }
        }
        List<Tenant> signers = lease.getTenants();

        leaseRepository.save(lease);
        Optional<Apartment> apartment = apartmentRepository.findByApartmentNumber(lease.getApartment().getApartmentNumber());
        log.info("lease status got: {}", lease);
        return LeaseDTO.builder().id(lease.getId()).startDate(zonedDateToString(lease.getStartDate())).endDate(zonedDateToString(lease.getEndDate()))
                .apartment(apartment.orElseThrow()).externalId(lease.getExternalId()).signatureRequestGetResponse(result).tenants(signers).build();
    }


    ZonedDateTime parseZonedDateTime(String dateStr) {
        return LocalDate.parse(dateStr).atStartOfDay(ZoneId.of("UTC"));
    }

    String zonedDateToString(ZonedDateTime zonedDateTime) {
        return zonedDateTime.toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    Lease getById(Long id) {
        return leaseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lease not found"));
    }

    public List<LeaseDTO> getAll() {
        List<Lease> leases = leaseRepository.findAll();
        List<LeaseDTO> leaseDTOs = leases.stream()
            .map(lease -> LeaseDTO.builder()
                    .id(lease.getId())
                    .startDate(zonedDateToString(lease.getStartDate()))
                    .endDate(zonedDateToString(lease.getEndDate()))
                    .apartment(lease.getApartment())
                    .externalId(lease.getExternalId())
                    .tenants(lease.getTenants())
                    .build())
            .collect(Collectors.toList());
        return  leaseDTOs;
    }

    public Lease update(Long id, LeaseDTO leaseDetails) {
        Lease lease = getById(id);
        lease.setStatus(DocStatus.valueOf(leaseDetails.getStatus()));
        return leaseRepository.save(lease);
    }

    public void delete(Long id) {
        Lease lease = getById(id);
        leaseRepository.delete(lease);
    }
}
