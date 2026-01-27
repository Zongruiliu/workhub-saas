package com.workhub.api;

import com.workhub.app.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {

    @ExceptionHandler(AuthService.EmailAlreadyUsedException.class)
    public ProblemDetail handleEmailUsed(AuthService.EmailAlreadyUsedException ex){
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        pd.setTitle("Email Already Used");
        pd.setDetail("The email is already registered");
        pd.setProperty("email", ex.getEmail());
        return pd;
    }
}
