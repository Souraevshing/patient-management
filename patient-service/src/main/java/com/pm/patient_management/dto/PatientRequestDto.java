package com.pm.patient_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatientRequestDto {

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Maximum 50 characters")
    private String name;

    @NotBlank(message = "Email id is required")
    @Email(message = "Enter valid email")
    private String emailId;

    @NotBlank(message = "Address is required")
    @Size(max = 100, message = "Maximum 100 characters")
    private String address;

    @NotBlank(message = "Date of birth is required")
    private String dateOfBirth;

    @NotBlank(message = "Date of registration is required")
    private String registrationDate;

}
