package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.File;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "details to be passed along with requests related to document signatures")
public class LeaseSignRequestDTO {
    @Schema(description = "List of signer email addresses. Due to dropbox Signature free plan. It's limited to one", example = "signer1@example.com")
    String signerEmail;
    @Nullable
    @Schema(description = "optional username for creating test user just for test purposes ")
    String signerUserName;

    @NonNull
    @Schema(description = "Apartment number associated with the lease", example = "101")
    Long apartmentNumber;

    @JsonIgnore
    @Schema(description = "File to be signed", hidden = true)
    File file;

    @Size(min = 1)
    @Schema(description = "List of CC email addresses", example = "[\"cc1@example.com\", \"cc2@example.com\"]")
    List<String> ccEmails;

    @JsonIgnore
    @Schema(description = "Metadata associated with the document", hidden = true)
    MetaData metaData;
}
