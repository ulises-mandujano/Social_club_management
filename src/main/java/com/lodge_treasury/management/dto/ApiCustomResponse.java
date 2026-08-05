package com.lodge_treasury.management.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiCustomResponse<T> {
    private String status;
    private String message;
    private T data;

    public static <T> ApiCustomResponse<T> success (String message, T data) {
        return new ApiCustomResponse<>("success", message, data);
    }

    public static <T> ApiCustomResponse<T> success (String message) {
        return new ApiCustomResponse<>("sucess", message, null);
    }

    public static <T> ApiCustomResponse<T> error (String message) {
        return new ApiCustomResponse<>("error", message, null);
    }
}
