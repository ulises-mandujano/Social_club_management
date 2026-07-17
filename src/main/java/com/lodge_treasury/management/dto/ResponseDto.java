package com.lodge_treasury.management.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
@Schema(
        name = "Response",
        description = "Standard response format for successful operations"
)
public class ResponseDto {

    @Schema(
            description = "HTTP status code or application-specific status",
            example = "201"
    )
    private String statusCode;

    @Schema(
            description = "Descriptive message about the operation result",
            example = "Mason registered successfully"
    )
    private String statusMsg;
}
