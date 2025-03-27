package com.example.demo.entities;

import com.example.demo.util.enums.DocStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Builder
@Data
@Table(name = "leases")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Lease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(nullable = false, name = "lease_start_date")
    ZonedDateTime startDate;
    @Column(nullable = false, name = "lease_end_date")
    ZonedDateTime endDate;
    @Column(nullable = false, name = "external_id")
    String externalId;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    DocStatus status;
    @Column(name = "apartment_number")
    Long apartmentNumber;
    @ManyToOne
    User user;
    @Column(name = "dropbox_document_download_url")
    String dropboxDocumentUrl;
}
