package com.example.demo.controller;

import com.dropbox.sign.ApiException;
import com.dropbox.sign.model.SignatureRequestGetResponse;
import com.example.demo.dto.LeaseSignRequestDTO;
import com.example.demo.dto.MetaData;
import com.example.demo.entities.Lease;
import com.example.demo.services.LeaseManagementDropBoxImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentManagementController {
    final LeaseManagementDropBoxImpl leaseManagementDropBox;

    @Operation(
            summary = "Send document request via Dropbox signature services",
            description = "Sends signature request and saves to database for a valid user",
            requestBody = @RequestBody(
                    content = {
                            @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE),
                            @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)
                    }
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lease successfully sent",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SignatureRequestGetResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Error.class)))
            }
    )
    @PostMapping(path = "/send",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SignatureRequestGetResponse> sendLeaseSignatureRequest(
            @RequestParam(value = "file", required = false) MultipartFile file, // multipart/form-data
            @RequestBody(required = false) byte[] fileData, // application/octet-stream
            @RequestPart(value = "leaseSignatureRequestDetails", required = false) LeaseSignRequestDTO leaseSignRequestDTO,
            @RequestPart(value = "metaData", required = false) MetaData metaData) throws Exception {

        Path document;
        if (file != null) {
            document = Files.createTempFile("lease", ".tmp");
            file.transferTo(document);
        }
        else if (fileData != null) {
            document = Files.createTempFile("lease", ".tmp");
            Files.write(document, fileData);
        } else {
            return ResponseEntity.badRequest().body(null); // No file provided
        }

        leaseSignRequestDTO.setFile(document.toFile());
        leaseSignRequestDTO.setMetaData(metaData);
        SignatureRequestGetResponse body = leaseManagementDropBox.createLeaseSignatureRequest(leaseSignRequestDTO);
        return ResponseEntity.ok(body);
    }

    @Operation(
            summary = "send request to cancel, needs to wait for a callback",
            description = "uses external id from dropbox to send request to cancel to dropbox"
            ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "notification received, pending"),
                    @ApiResponse(responseCode = "4XX", description = "bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
            }
    )
    @PostMapping(path = "/cancel")
    ResponseEntity<?> sendLeaseSignatureRequest(@RequestParam Long leaseId) throws ApiException {
        leaseManagementDropBox.cancelLeaseSignatureRequest(leaseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "get signature status",
            description = "uses external id from dropbox to pull"
            ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "lease status received",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SignatureRequestGetResponse.class))),
                    @ApiResponse(responseCode = "4XX", description = "bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
            }
    )
    @GetMapping(path = "/get", produces = "application/json")
    ResponseEntity<Lease> getLeaseSignatureStatus(@RequestParam Long leaseId) throws ApiException {
        //todo try out a mapstruct here
        return new ResponseEntity<>(leaseManagementDropBox.getLeaseStatus(leaseId), HttpStatus.OK);
    }

    @Operation(
            summary = "callback for dropbox to update status",
            description = "uses our lease ID to read stored external id provided by dropbox to get the most recent status of specific doc"
            ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "db updated"
                    ),
                    @ApiResponse(responseCode = "4XX", description = "bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
            }
    )
    @GetMapping(path = "/update")
    ResponseEntity<SignatureRequestGetResponse> updateStatus(@RequestParam Long leaseId) throws ApiException {
        leaseManagementDropBox.dropboxCallback(leaseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
