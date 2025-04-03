package com.pm.patient_management.service;

import com.pm.patient_management.dto.HttpResponseDto;
import com.pm.patient_management.dto.PatientRequestDto;

import java.util.UUID;

public interface PatientService {

    HttpResponseDto findAllPatients();

    HttpResponseDto findPatientById(UUID id);

    HttpResponseDto findPatientByEmailId(String emailId);

    HttpResponseDto registerPatient(PatientRequestDto patient);

    HttpResponseDto updatePatientById(UUID id, PatientRequestDto patientRequestDto);

    HttpResponseDto deletePatientById(UUID id);

}
