package com.lodge_treasury.management.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
@Schema(
        name = "ErrorResponse",
        description = "Standard error response format for the API"
)
public class ErrorResponseDto {

    @Schema(
            description = "Path of the request that caused the error",
            example = "/api/register"
    )
    private String apiPath;

    @Schema(
            description = "HTTP status code of the error",
            example = "BAD_REQUEST",
            allowableValues = {"BAD_REQUEST", "NOT_FOUND", "INTERNAL_SERVER_ERROR", "CONFLICT"}
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Human readable error message",
            example = "Birth date cannot be null"
    )
    private String message;

    @Schema(
            description = "Timestamp when the error occurred",
            example = "2026-06-14T19:30:00"
    )
    private LocalDateTime errorTime;
}
