package com.autonext.code.autonext_server.services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.exceptions.ErrorSendEmailException;
import com.autonext.code.autonext_server.exceptions.UserNotFoundException;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.repositories.UserRepository;

@Service
public class PasswordResetService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailTemplateService emailTemplateService;

    public PasswordResetService(JwtService jwtService, EmailSenderService emailSenderService, UserRepository userRepository, PasswordEncoder passwordEncoder, EmailTemplateService emailTemplateService) {
        this.emailTemplateService = emailTemplateService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public String sendPasswordResetEmail(String email) {

        Optional<User> userOptional = userRepository.findByEmail(email);
    
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Email incorrecto.");
        }

        User user = userOptional.get();

        String token = jwtService.generateTokenPassword(user);

        try {
            emailTemplateService.sendResetPasswordEmail(email, token);
            return token;
        } catch (Exception e) {
            throw new ErrorSendEmailException("Error al enviar el email");
        }

    }

    public boolean resetPassword(String token, String newPassword) {
        try {

            if (jwtService.isTokenExpired(token)) {
                return false;
            }

            int userId = jwtService.extractUserId(token);
            Optional<User> userOptional = userRepository.findById(userId);
    
            if (userOptional.isEmpty()) {
                return false;
            }
    
            User user = userOptional.get();
    
            String encryptedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encryptedPassword);
            userRepository.save(user);
            return true;

        } catch (Exception e) {
            return false;
        }
    }
    
}

