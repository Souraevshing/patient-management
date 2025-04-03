package com.pm.patient_management.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalException {

    private static final Logger log = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handle(MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();
        exception
                .getBindingResult()
                .getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, String>> patientNotFoundException(
            PatientNotFoundException exception
    ) {

        log.error("Patient does not exist {}", exception.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Patient does not exist");
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<Map<String, String>> emailAlreadyExistException(
            EmailAlreadyExistException exception
    ) {

        log.error("Email already exist {}", exception.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Email already exist");
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }

}
