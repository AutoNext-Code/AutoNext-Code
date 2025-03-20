package com.autonext.code.autonext_server.services;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.exceptions.UserAlreadyExistsException;
import com.autonext.code.autonext_server.models.Car;
import com.autonext.code.autonext_server.models.Role;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.repositories.CarRepository;
import com.autonext.code.autonext_server.repositories.UserRepository;

import jakarta.mail.MessagingException;


@Service
public class AuthService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Value("${url.client}")
    private String clientUrl;

    public AuthService(UserRepository userRepository, CarRepository carRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtService jwtService) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public void register(String email, String name, String surname, String password, String carPlate) {
        if (name.isEmpty() || surname.isEmpty() || carPlate.isEmpty()) {
            throw new RuntimeException("Los campos no pueden estar vacíos");
        }

        if (!isValidEmail(email)) {
            throw new RuntimeException("El formato de email es incorrecto");
        }

        if (!isValidPassword(password)) {
            throw new RuntimeException("La contraseña no cumple con los requisitos de seguridad");
        }

        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("El usuario ya existe");
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

        Car car = new Car(carPlate, user);
        user.setCars(List.of(car));

        userRepository.save(user);
        carRepository.save(car);

    }

    public String login(String email, String password) {
         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

    return jwtService.generateToken(user);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        return pattern.matcher(password).matches();
    }

     public void registerUser(String email, String token) {
        // Lógica para registrar al usuario y generar el token de confirmación

        String confirmationLink = clientUrl + "/email-confirmation/" + token;
        String htmlContent = "<html>"
                           + "<body>"
                           + "<h1>Confirmación de Registro</h1>"
                           + "<p>Gracias por registrarte. Por favor, haz clic en el siguiente enlace para confirmar tu registro:</p>"
                           + "<a href=\"" + confirmationLink + "\">Confirmar Registro</a>"
                           + "</body>"
                           + "</html>";

        try {
            emailSenderService.sendHtmlEmail(email, "Confirmación de Registro", htmlContent);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}