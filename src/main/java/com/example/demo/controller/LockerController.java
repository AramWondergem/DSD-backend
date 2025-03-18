package com.example.demo.controller;

import com.example.demo.dto.request.LockerRequest;
import com.example.demo.dto.response.LockerResponse;
import com.example.demo.entities.Locker;
import com.example.demo.mappers.LockerMapper;
import com.example.demo.services.LockerService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class LockerController {

    private final LockerService lockerService;
    private final LockerMapper lockerMapper;

    @Operation(
            summary = "Updates a locker",
            description = "Updates the locker with the specified ID. If the `apartmentNumber` is set to `null`, the locker will be marked as empty. The options for locker ids are 1 until 5. The options for apartment numbers is 1 until 7.",
            parameters = {
                   @Parameter(name = "id", in = ParameterIn.PATH, description = "locker id")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Locker updated successfully.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LockerResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Locker not found.")
            }
    )
    @PatchMapping("locker/{id}")
    public ResponseEntity<LockerResponse> updateLocker(@PathVariable Long id, @RequestBody LockerRequest request) {
        Locker updatedLocker = lockerService.update(id,request);

        LockerResponse response = lockerMapper.map(updatedLocker);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Retrieves all lockers in a building",
            description = "Fetches a list of all lockers for the building with the specified ID. THere is only one building in the backend and that is building with id 1",
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
        List<Locker> list = this.lockerService.getAllByBuildingId(id);
        List<LockerResponse> DTOlist = this.lockerMapper.map(list);
        return ResponseEntity.ok(DTOlist);
    }

    @Operation(
            summary = "Retrieves all lockers assigned to a user",
            description = "Fetches a hashmap of all lockers assigned to the user with the specified ID. The reponse type below is not correct. I do not know how to make it correct. Try out the endpoint and see what the reponse type is. There are 12 users in the backend with id 1 until 12. I do not how to make clear what the except reponse schema is ",
            parameters = {
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "user id")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of lockers retrieved successfully.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = HashMap.class)))
            }
    )
    @GetMapping("users/{id}/lockers")
    public ResponseEntity<Map<Long,List<LockerResponse>>> getAllLockersByUser(@PathVariable Long id) {
        Map<Long,List<Locker>> map = this.lockerService.getAllByUserId(id);
        Map<Long,List<LockerResponse>> dataResponse = this.lockerMapper.map(map);
        return ResponseEntity.ok(dataResponse);
    }

}
