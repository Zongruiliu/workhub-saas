package com.workhub.api;

import com.workhub.app.AuthService;
import com.workhub.app.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthService.EmailAlreadyUsedException.class)
    public ProblemDetail handleEmailUsed(AuthService.EmailAlreadyUsedException ex){
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        pd.setTitle("Email Already Used");
        pd.setDetail("The email is already registered");
        pd.setProperty("email", ex.getEmail());
        return pd;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleBadCredentials(BadCredentialsException ex) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        pd.setTitle("Invalid credentials");
        pd.setDetail("Email or password is incorrect.");
        return pd;
    }

    @ExceptionHandler(ProjectService.NotFoundException.class)
    public ResponseEntity<?> notFound(ProjectService.NotFoundException ex) {
        return ResponseEntity.status(404).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> badRequest(IllegalArgumentException ex) {
        return ResponseEntity.status(400).body(Map.of("message", ex.getMessage()));
    }

}
