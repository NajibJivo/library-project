package com.dk.ek.libraryproject.common;

import com.dk.ek.libraryproject.shared.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1) Dine domænefejl (NotFound/BadRequest m.fl.) -> korrekt HTTP-kode + ProblemDetail
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ProblemDetail> handleApi(
            ApiException ex,
            HttpServletRequest req) {

        var pd = ProblemDetail.forStatusAndDetail(ex.status(), ex.getMessage());
        pd.setTitle(ex.code());   // fx NOT_FOUND eller BAD_REQUEST
        pd.setProperty("path", req.getRequestURI());
        pd.setProperty("timestamp", LocalDateTime.now());
        return ResponseEntity.status(ex.status()).body(pd);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest req) {

        List<String> errors = new ArrayList<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            errors.add(fe.getDefaultMessage());
        }

        var pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed");
        pd.setTitle("VALIDATION_ERROR");
        pd.setProperty("path", req.getRequestURI());
        pd.setProperty("timestamp", java.time.LocalDateTime.now());
        pd.setProperty("errors", errors);
        return ResponseEntity.badRequest().body(pd);
    }

    // 3) Type-mismatch (så /api/works/{} eller /api/works/abc bliver 400 i stedet for 500):
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProblemDetail> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest req) {

        var expected = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown";
        var pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "Invalid value for '" + ex.getName() + "': '" + ex.getValue() + "'. Expected: " + expected);
        pd.setTitle("BAD_REQUEST");
        pd.setProperty("path", req.getRequestURI());
        pd.setProperty("timestamp", LocalDateTime.now());
        return ResponseEntity.badRequest().body(pd);
    }

    // 4) Ugyldigt/malformed JSON body -> 400
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleUnreadable(
            HttpMessageNotReadableException ex,
            HttpServletRequest req) {

        var pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Malformed request body");
        pd.setTitle("BAD_REQUEST");
        pd.setProperty("path", req.getRequestURI());
        pd.setProperty("timestamp", LocalDateTime.now());
        return ResponseEntity.badRequest().body(pd);
    }

    // 5) Fallback (uventede fejl) -> 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleAny(Exception ex, HttpServletRequest req) {
        var pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error");
        pd.setTitle("INTERNAL_ERROR");
        pd.setProperty("path", req.getRequestURI());
        pd.setProperty("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(pd);
    }


}
