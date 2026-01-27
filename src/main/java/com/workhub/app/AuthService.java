package com.workhub.app;

import com.workhub.api.auth.RegisterRequest;
import com.workhub.api.auth.RegisterResponse;
import com.workhub.domain.User;
import com.workhub.infra.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public RegisterResponse register(RegisterRequest req){
        String email = req.getEmail().trim().toLowerCase();

        if(userRepository.existsByEmail(email)){
            throw new EmailAlreadyUsedException(email);
        }

        User u = new User();
        u.setEmail(email);
        u.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        u.setStatus("ACTIVE");

        User saved = userRepository.save(u);
        return new RegisterResponse(saved.getId(), saved.getEmail());
    }

    public static class EmailAlreadyUsedException extends RuntimeException{
        private final String email;
        public EmailAlreadyUsedException(String email) {
            super("Email Already Used: " + email);
            this.email = email;
        }
        public String getEmail() { return email; }
    }
}
