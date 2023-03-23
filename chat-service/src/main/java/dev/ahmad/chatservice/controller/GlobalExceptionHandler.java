package dev.ahmad.chatservice.controller;

import dev.ahmad.chatservice.exception.MessageNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<String> messageNotFoundExceptionHandler(MessageNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                .body(exception.getMessage());
    }

}
