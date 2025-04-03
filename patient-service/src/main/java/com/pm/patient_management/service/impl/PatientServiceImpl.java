package com.pm.patient_management.service.impl;

import com.pm.patient_management.dto.HttpResponseDto;
import com.pm.patient_management.dto.PatientRequestDto;
import com.pm.patient_management.dto.PatientResponseDto;
import com.pm.patient_management.exception.EmailAlreadyExistException;
import com.pm.patient_management.exception.PatientNotFoundException;
import com.pm.patient_management.mapper.PatientMapper;
import com.pm.patient_management.model.Patient;
import com.pm.patient_management.repository.PatientRepository;
import com.pm.patient_management.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    public HttpResponseDto findAllPatients() {

        try {
            List<PatientResponseDto> allPatients = patientRepository
                    .findAll()
                    .stream()
                    .map(patientMapper::mapToResponseDto)
                    .toList();
            return HttpResponseDto
                    .builder()
                    .status(HttpStatus.OK.value())
                    .message("Patients retrieved successfully")
                    .data(allPatients)
                    .build();
        } catch (Exception e) {
            return HttpResponseDto
                    .builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .error("Something went wrong" + e.getMessage())
                    .build();
        }

    }

    @Override
    public HttpResponseDto findPatientById(UUID id) {

        try {
            Optional<Patient> existingPatient = patientRepository
                    .findById(id);
            if (existingPatient.isEmpty()) {
                return HttpResponseDto
                        .builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .error("Patient not found")
                        .build();
            }
            return HttpResponseDto
                    .builder()
                    .status(HttpStatus.OK.value())
                    .message("Patient retrieved successfully")
                    .data(patientMapper.mapToResponseDto(existingPatient.get()))
                    .build();
        } catch (Exception e) {
            return HttpResponseDto
                    .builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .error("Something went wrong" + e.getMessage())
                    .build();
        }

    }

    @Override
    public HttpResponseDto findPatientByEmailId(String emailId) {

        try {
            Optional<Patient> patientFound = patientRepository
                    .findByEmailId(emailId);
            if (patientFound.isEmpty()) {
                throw new PatientNotFoundException("Email does not exist" + emailId);
            }
            return HttpResponseDto
                    .builder()
                    .status(HttpStatus.OK.value())
                    .message("Patient found with email")
                    .data(patientMapper.mapToResponseDto(patientFound.get()))
                    .build();
        } catch (PatientNotFoundException e) {
            return HttpResponseDto
                    .builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .error(e.getMessage())
                    .build();
        } catch (Exception e) {
            return HttpResponseDto
                    .builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .error("Something went wrong" + e.getMessage())
                    .build();
        }

    }

    @Override
    public HttpResponseDto registerPatient(PatientRequestDto patientRequestDto) {

        try {
            if (patientRepository.findByEmailId(patientRequestDto.getEmailId()).isPresent()) {
                throw new EmailAlreadyExistException("Email already exist" + patientRequestDto.getEmailId());
            }
            Patient newPatient = patientRepository.save(patientMapper.mapToEntity(patientRequestDto));
            return HttpResponseDto
                    .builder()
                    .status(HttpStatus.CREATED.value())
                    .message("Patient registered successfully")
                    .data(patientMapper.mapToResponseDto(newPatient))
                    .build();
        } catch (EmailAlreadyExistException e) {
            return HttpResponseDto
                    .builder()
                    .status(HttpStatus.CONFLICT.value())
                    .error(e.getMessage())
                    .build();
        } catch (Exception e) {
            return HttpResponseDto
                    .builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .error("Failed to register patient" + e.getMessage())
                    .build();
        }

    }

    @Override
    public HttpResponseDto updatePatientById(UUID id, PatientRequestDto patientRequestDto) {

        try {
            Optional<Patient> existingPatient = patientRepository
                    .findById(id);
            if (existingPatient.isEmpty()) {
                return HttpResponseDto
                        .builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .error("Patient not found")
                        .build();
            }
            Patient patient = existingPatient.get();
            patient.setName(patientRequestDto.getName());
            patient.setAddress(patientRequestDto.getAddress());
            patient.setEmailId(patientRequestDto.getEmailId());
            patient.setDateOfBirth(LocalDate.parse(patientRequestDto.getDateOfBirth()));
            patient.setRegistrationDate(LocalDate.parse(patientRequestDto.getRegistrationDate()));
            Patient updatedPatient = patientRepository.save(patient);
            return HttpResponseDto
                    .builder()
                    .status(HttpStatus.OK.value())
                    .message("Patient updated successfully")
                    .data(patientMapper.mapToResponseDto(updatedPatient))
                    .build();
        } catch (EmailAlreadyExistException e) {
            return HttpResponseDto
                    .builder()
                    .status(HttpStatus.CONFLICT.value())
                    .error(e.getMessage())
                    .build();
        } catch (Exception e) {
            return HttpResponseDto
                    .builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .error("Something went wrong" + e.getMessage())
                    .build();
        }

    }

    public HttpResponseDto deletePatientById(UUID id) {

        try {
            Optional<Patient> existingPatient = patientRepository
                    .findById(id);
            if (existingPatient.isEmpty()) {
                return HttpResponseDto
                        .builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .error("Patient not found")
                        .build();
            }
            Patient deletedPatient = existingPatient.get();
            patientRepository.deleteById(id);
            return HttpResponseDto
                    .builder()
                    .status(HttpStatus.OK.value())
                    .message("Patient deleted successfully")
                    .data(patientMapper.mapToResponseDto(deletedPatient))
                    .build();
        } catch (PatientNotFoundException e) {
            return HttpResponseDto
                    .builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .error(e.getMessage())
                    .build();
        } catch (Exception e) {
            return HttpResponseDto
                    .builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .error("Failed to delete patient" + e.getMessage())
                    .build();
        }

    }

}
