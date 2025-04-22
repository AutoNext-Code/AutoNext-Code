package com.autonext.code.autonext_server.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.hibernate.StaleStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.autonext.code.autonext_server.dto.PasswordChangingDTO;
import com.autonext.code.autonext_server.dto.UserDto;
import com.autonext.code.autonext_server.dto.UserRequestDTO;
import com.autonext.code.autonext_server.exceptions.NoChangesMadeException;
import com.autonext.code.autonext_server.exceptions.UserNotFoundException;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getProfile() {

        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                .getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        int userId = user.getId();

        try {
            UserDto userDto = userService.getProfile(userId);
            return ResponseEntity.ok(userDto);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editProfile(@RequestBody UserRequestDTO userRequestDto) {
        try {
            userService.editProfile(userRequestDto);
            return ResponseEntity.ok("Se han realiza los cambios correctamente");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no existe");
        } catch (NoChangesMadeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No hay cambio de datos en el perfil del usuario");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
          }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PatchMapping("/password-edit")
    public ResponseEntity<String> patchPassword(@Valid @RequestBody PasswordChangingDTO request) {

        try {
            userService.updatePassword(request.getNewPassword(), request.getOldPassword()) ;
            return ResponseEntity.ok("Contraseña actualizada correctamente");
        } catch (StaleStateException sse) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        } 

    }

}
