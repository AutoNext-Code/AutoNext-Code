package com.autonext.code.autonext_server.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.models.Role;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.repositories.UserRepository;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtService jwtService) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public void register(String email, String name, String surname, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setSurname(surname);
        user.setPassword(passwordEncoder.encode(password)); 
        user.setRole(Role.User); 
        user.setBanned(false);
        user.setEmailConfirm(false);
        user.setConfirmationToken("");

        userRepository.save(user);
    }

    public String login(String email, String password) {
         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

    return jwtService.generateToken(user);
    }
}
