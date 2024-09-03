package com.prepmate.backend.exception;

public record ErrorResponse(int statusCode, String message) {
}
