package com.example.demo.controller;

import com.example.demo.dto.request.LockerRequest;
import com.example.demo.dto.response.LockerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class LockerController {

    @Operation(
            summary = "Updates a locker assignment",
            description = "Updates the locker with the specified ID. If the `apartmentNumber` is set to `null`, the locker will be marked as empty.",
            parameters = {
                   @Parameter(name = "id", in = ParameterIn.PATH, description = "locker id")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Locker updated successfully.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LockerResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Locker not found.")
            }
    )
    @PutMapping("locker/{id}")
    public ResponseEntity<LockerResponse> updateLocker(@PathVariable Long id, @RequestBody LockerRequest request) {
        LockerResponse response = LockerResponse.builder()
                .id(id)
                .apartmentNumber(request.getApartmentNumber())
                .lockerNumber(1).build();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Retrieves all lockers in a building",
            description = "Fetches a list of all lockers for the building with the specified ID.",
            parameters = {
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "building id")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of lockers retrieved successfully.",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = LockerResponse.class))))
            }
    )
    @GetMapping("buildings/{id}/lockers")
    public ResponseEntity<List<LockerResponse>> getAllLockersByBuilding(@PathVariable Long id) {
        return ResponseEntity.ok(new ArrayList<>());
    }

    @Operation(
            summary = "Retrieves all lockers assigned to a user",
            description = "Fetches a list of all lockers assigned to the user with the specified ID.",
            parameters = {
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "user id")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of lockers retrieved successfully.",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = LockerResponse.class))))
            }
    )
    @GetMapping("users/{id}/lockers")
    public ResponseEntity<List<LockerResponse>> getAllLockersByUser(@PathVariable Long id) {
        return ResponseEntity.ok(new ArrayList<>());
    }

}
