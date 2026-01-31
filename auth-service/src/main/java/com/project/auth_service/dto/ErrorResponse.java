package com.project.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private Instant timestamp;
}
