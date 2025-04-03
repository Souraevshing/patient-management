package com.pm.patient_management.mapper;

import com.pm.patient_management.dto.PatientRequestDto;
import com.pm.patient_management.dto.PatientResponseDto;
import com.pm.patient_management.model.Patient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PatientMapper {

    public PatientResponseDto mapToResponseDto(Patient patient) {
        return new PatientResponseDto(
                patient.getId().toString(),
                patient.getName(),
                patient.getEmailId(),
                patient.getAddress(),
                patient.getDateOfBirth().toString()
        );
    }

    public Patient mapToEntity(PatientRequestDto patientRequestDto) {
        Patient patient = new Patient();
        patient.setName(patientRequestDto.getName());
        patient.setEmailId(patientRequestDto.getEmailId());
        patient.setAddress(patientRequestDto.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDto.getDateOfBirth()));
        patient.setRegistrationDate(LocalDate.parse(patientRequestDto.getRegistrationDate()));
        return patient;
    }
}
