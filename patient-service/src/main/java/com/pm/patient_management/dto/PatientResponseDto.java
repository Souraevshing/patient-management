package com.pm.patient_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PatientResponseDto {

    private String id;
    private String name;
    private String emailId;
    private String address;
    private String dateOfBirth;

}
