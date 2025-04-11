package com.autonext.code.autonext_server.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.autonext.code.autonext_server.exceptions.UserNotFoundException;
import com.autonext.code.autonext_server.services.AuthService;
import com.autonext.code.autonext_server.services.PasswordResetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;
  private final PasswordResetService passwordResetService;

  public AuthController(AuthService authService, PasswordResetService passwordResetService) {
    this.passwordResetService = passwordResetService;
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
    } catch (UserNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email o contraseña incorrectos");
    } catch (EmailNotConfirmedException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Debes confirmar tu correo antes de iniciar sesión.");
    } catch (BadCredentialsException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
    }
  }

  @PutMapping("/email-confirmation")
  public ResponseEntity<String> emailConfirm(@RequestBody String token) {
    try {
      authService.confirmEmail(token);
      return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE).body("Correo Validado");
    } catch (InvalidTokenException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token no válido");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
    }
  }

  @PostMapping("/reset-password/")
  public ResponseEntity<String> requestPasswordReset(@RequestBody String email) {
    String token = passwordResetService.sendPasswordResetEmail(email);
    return ResponseEntity.ok(token);
  }

  @PutMapping("/reset-password/{token}")
  public ResponseEntity<String> resetPassword(@PathVariable String token, @RequestBody String newPassword) {
    if (passwordResetService.resetPassword(token, newPassword)) {
      return ResponseEntity.ok("Contraseña restablecida con éxito");
    } else {
      return ResponseEntity.badRequest().body("Token inválido o expirado");
    }
  }

}
