package com.prepmate.backend.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalHandle(IllegalArgumentException e) {
        log.error("[exceptionHandle] ", e);
        return new ErrorResult("BAD_REQUEST", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult serverErrorHandle(HttpServerErrorException.InternalServerError e) {
        log.error("[exceptionHandle] ", e);
        return new ErrorResult("INTERNAL_SERVER_ERROR", "서버 내부 오류"); }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ErrorResult handleNotFoundException(NoHandlerFoundException e) {
        log.error("[exceptionHandle] ", e);
        return new ErrorResult("NOT_FOUND", "Resource not found");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult handleValidationExceptions(MethodArgumentNotValidException e) {
        log.error("[exceptionHandle] ", e);
        StringBuilder errorMessage = new StringBuilder("Validation failed for the request: ");
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorMessage.append(fieldError.getField()).append(" - ").append(fieldError.getDefaultMessage()).append("; ");
        }
        return new ErrorResult("VALIDATION_ERROR", errorMessage.toString());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResult handleEntityNotFoundException(EntityNotFoundException e){
        log.error("[exceptionHandle] ", e);
        return new ErrorResult("NOT_FOUND", e.getMessage());
    }
}
