package com.pm.patient_management.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HttpResponseDto {

    private int status;
    private String error;
    private String message;
    private Object data;

}
