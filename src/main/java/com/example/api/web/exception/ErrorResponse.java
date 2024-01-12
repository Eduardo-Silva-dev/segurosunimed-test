package com.example.api.web.exception;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ErrorResponse {

    private String error;

    private String message;

    private Map<String, String> errors = new HashMap<>();

    public void addError(String field, String defaultMessage) {
        errors.put(field, defaultMessage);
    }
}
