package ru.skypro.auction.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LotExceptionHandler {
    @ExceptionHandler(LotNotFoundException.class)
    public ResponseEntity<?> handleNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(LotStatusNotStartedException.class)
    public ResponseEntity<?> handleStatusNotStarted() {
        return ResponseEntity.badRequest().build();
    }
}
