package com.autonext.code.autonext_server.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.autonext.code.autonext_server.dto.LoginRequest;
import com.autonext.code.autonext_server.dto.RegisterRequest;
import com.autonext.code.autonext_server.exceptions.CarPlateAlreadyExistsException;
import com.autonext.code.autonext_server.exceptions.EmailNotConfirmedException;
import com.autonext.code.autonext_server.exceptions.ErrorSendEmailException;
import com.autonext.code.autonext_server.exceptions.InvalidTokenException;
import com.autonext.code.autonext_server.exceptions.UserAlreadyExistsException;
import com.autonext.code.autonext_server.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
    try {
      authService.register(request.getEmail(), request.getName(),
          request.getSurname(), request.getPassword(), request.getCarPlate());
      return ResponseEntity.ok("Usuario registrado correctamente");
    } catch (UserAlreadyExistsException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe");
    } catch (CarPlateAlreadyExistsException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("La matrícula ya está registrada");
    } catch (ErrorSendEmailException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Error al enviar el email de confirmación");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos de registro inválidos");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
    }
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginRequest request) {
    try {
      String token = authService.login(request.getEmail(), request.getPassword());
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
          .body(token);
    } catch (BadCredentialsException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
    } catch (EmailNotConfirmedException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Debes confirmar tu correo antes de iniciar sesión.");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
    }
  }

  @PutMapping("/email-confirmation")
  public ResponseEntity<String> emailConfirm(@RequestBody String token ) {
    try {
      authService.confirmEmail(token) ;
      return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE).body("Correo Validado");
    } catch (InvalidTokenException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token no válido");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
    }
  }

}
