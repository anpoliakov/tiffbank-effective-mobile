package by.anpoliakov.tiffbank.controller;

import by.anpoliakov.tiffbank.util.exception.*;
import by.anpoliakov.tiffbank.domain.dto.UserExceptionDto;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
@Slf4j
public class ErrorHandlingControllerAdvice {
    @ExceptionHandler
    private ResponseEntity<UserExceptionDto> handleException(ValidationException e) {
        log.warn("Data validation error: {}", e.getMessage());
        UserExceptionDto response = new UserExceptionDto(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<UserExceptionDto> handleException(UserNotFoundException e) {
        log.warn("User in data base is not found: {}", e.getMessage());
        UserExceptionDto response = new UserExceptionDto(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<UserExceptionDto> handleException(UserDataException e) {
        log.warn("Exception in user data: {}", e.getMessage());
        UserExceptionDto response = new UserExceptionDto(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<UserExceptionDto> handleException(TransferException e) {
        log.error("Exception during transaction {}", e.getMessage());
        UserExceptionDto response = new UserExceptionDto(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<UserExceptionDto> handleException(DateTimeParseException e) {
        log.warn("Exception during valid date: {}", e.getMessage());
        UserExceptionDto response = new UserExceptionDto(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<UserExceptionDto> handleException(SearchException e) {
        log.warn("Exception during searching operation {}", e.getMessage());
        UserExceptionDto response = new UserExceptionDto(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
