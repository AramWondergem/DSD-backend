package com.example.demo.controller;

import com.example.demo.dto.request.ComplaintRequest;
import com.example.demo.dto.response.ComplaintResponse;
import com.example.demo.entities.Complaint;
import com.example.demo.mappers.ComplaintMapper;
import com.example.demo.services.ComplaintServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ComplaintController {
    private final ComplaintServiceImpl complaintService;
    private final ComplaintMapper complaintMapper;

    @Operation(summary = "Create a new complaint", description = "extracts username from jwt, need to be logged in", responses = {
            @ApiResponse(responseCode = "201", description = "Complaint created successfully", content = @Content(schema = @Schema(implementation = ComplaintResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/complaints")
    public ResponseEntity<ComplaintResponse> createComplaint(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Complaint details", required = true, content = @Content(examples = @ExampleObject(value = "{\"message\": \"This is a complaint message\"}")))
                                                             @RequestBody ComplaintRequest complaintDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Complaint createdComplaint = complaintService.createComplaint(complaintDTO, username);
        return new ResponseEntity<>(complaintMapper.mapToResponse(createdComplaint), HttpStatus.CREATED);
    }

    @Operation(summary = "Get a complaint by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Complaint retrieved successfully", content = @Content(schema = @Schema(implementation = ComplaintResponse.class))),
            @ApiResponse(responseCode = "404", description = "Complaint not found")
    })
    @GetMapping("/complaints/{id}")
    public ResponseEntity<ComplaintResponse> getComplaintById(@io.swagger.v3.oas.annotations.Parameter(description = "ID of the complaint to retrieve", required = true, example = "1") @PathVariable Long id) {
        Complaint complaint = complaintService.getComplaintById(id);
        return new ResponseEntity<>(complaintMapper.mapToResponse(complaint), HttpStatus.OK);
    }

    @Operation(summary = "Get all complaints by a user",
            parameters = {
            @Parameter(
                    name = "username",
                    description = "Username of the user to retrieve complaints for",
                    required = true,
                    example = "john_doe"
            )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Complaints retrieved successfully", content = @Content(schema = @Schema(implementation = ComplaintResponse.class, type = "array"))),
                    @ApiResponse(responseCode = "404", description = "User not found")
            })
    @GetMapping("users/complaints")
    public ResponseEntity<List<ComplaintResponse>> getComplaintById(@RequestParam String username) {
        List<Complaint> complaints = complaintService.getAllComplaintByUser(username);
        List<ComplaintResponse> list = complaints.stream().map(complaintMapper::mapToResponse).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "Get all complaints", responses = {
            @ApiResponse(responseCode = "200", description = "Complaints retrieved successfully", content = @Content(schema = @Schema(implementation = ComplaintResponse.class, type = "array")))
    })
    @GetMapping("/complaints")
    public ResponseEntity<List<ComplaintResponse>> getAllComplaints() {
        List<Complaint> complaints = complaintService.getAllComplaints();
        List<ComplaintResponse> list = complaints.stream().map(complaintMapper::mapToResponse).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "Update a complaint", responses = {
            @ApiResponse(responseCode = "200", description = "Complaint updated successfully", content = @Content(schema = @Schema(implementation = ComplaintResponse.class))),
            @ApiResponse(responseCode = "404", description = "Complaint not found")
    })
    @PutMapping("/complaints/{id}")
    public ResponseEntity<ComplaintResponse> updateComplaint(@io.swagger.v3.oas.annotations.Parameter(description = "ID of the complaint to update", required = true, example = "1") @PathVariable Long id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated complaint details", required = true, content = @Content(examples = @ExampleObject(value = "{\"message\": \"Updated complaint message\",\"status\": \"pending\"}"))) @RequestBody ComplaintRequest complaintRequest) {
        Complaint updatedComplaint = complaintService.updateComplaint(id, complaintRequest);
        return new ResponseEntity<>(complaintMapper.mapToResponse(updatedComplaint), HttpStatus.OK);
    }

    @Operation(summary = "Delete a complaint", responses = {
            @ApiResponse(responseCode = "204", description = "Complaint deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Complaint not found")
    })
    @DeleteMapping("/complaints/{id}")
    public ResponseEntity<Void> deleteComplaint(@io.swagger.v3.oas.annotations.Parameter(description = "ID of the complaint to delete", required = true, example = "1") @PathVariable Long id) {
        complaintService.deleteComplaint(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Delete all complaints by a user", responses = {
            @ApiResponse(responseCode = "204", description = "Complaints deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("users/complaints")
    public ResponseEntity<Void> deleteAllComplaint(@io.swagger.v3.oas.annotations.Parameter(description = "Username of the user to delete complaints for", required = true, example = "john_doe") @PathVariable String username) {
        complaintService.deleteAllComplaintsByUser(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Create a new complaint without jwt token for testing purposes", description = "for testing purposes. need to add valid username in header", responses = {
            @ApiResponse(responseCode = "201", description = "Complaint created successfully", content = @Content(schema = @Schema(implementation = ComplaintResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/complaints/test")
    public ResponseEntity<ComplaintResponse> createComplainTest(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Complaint details", required = true, content = @Content(examples = @ExampleObject(value = "{\"message\": \"This is a complaint message\"}")))
                                                                @RequestBody ComplaintRequest complaintDTO, @io.swagger.v3.oas.annotations.Parameter(name = "username", required = true, description = "username in header for testing purposes") @RequestHeader String username) {

        Complaint createdComplaint = complaintService.createComplaint(complaintDTO, username);
        return new ResponseEntity<>(complaintMapper.mapToResponse(createdComplaint), HttpStatus.CREATED);
    }
}
