package com.pm.patient_management.controller;

import com.pm.patient_management.dto.HttpResponseDto;
import com.pm.patient_management.dto.PatientRequestDto;
import com.pm.patient_management.service.impl.PatientServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Patient", description = "Handle patients data")
@RequestMapping("/api/v1/patients")
@RestController()
@AllArgsConstructor
public class PatientController {

    private final PatientServiceImpl patientServiceImpl;

    @Operation(
            summary = "Find all patients",
            description = "Fetch all patients",
            method = "GET"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patients retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping()
    public HttpResponseDto findAllPatients() {
        return patientServiceImpl.findAllPatients();
    }

    @Operation(summary = "Find patient by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient found successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("id/search_query")
    public HttpResponseDto findPatientById(@RequestParam("id") UUID uuid) {
        return patientServiceImpl.findPatientById(uuid);
    }

    @Operation(summary = "Find patient by email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient found successfully"),
            @ApiResponse(responseCode = "404", description = "Email does not exist"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("email/search_query")
    public HttpResponseDto findPatientByEmailId(@RequestParam("emailId") String emailId) {
        return patientServiceImpl.findPatientByEmailId(emailId);
    }

    @Operation(summary = "Register patient")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Patient registered successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid data"),
            @ApiResponse(responseCode = "409", description = "Conflict - Email already exists"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping()
    public HttpResponseDto registerPatient(
            @Validated()
            @RequestBody PatientRequestDto patientRequestDto
    ) {
        return patientServiceImpl.registerPatient(patientRequestDto);
    }

    @Operation(summary = "Update patient by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient updated successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "409", description = "Conflict - Email already exists"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("id/search_query")
    public HttpResponseDto updatePatientById(
            @RequestParam("id") UUID id,
            @Validated()
            @RequestBody PatientRequestDto patientRequestDto) {
        return patientServiceImpl.updatePatientById(id, patientRequestDto);
    }

    @Operation(summary = "Delete patient by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("id/search_query")
    public HttpResponseDto deletePatientById(
            @RequestParam("id") UUID id
    ) {
        return patientServiceImpl.deletePatientById(id);
    }

}
