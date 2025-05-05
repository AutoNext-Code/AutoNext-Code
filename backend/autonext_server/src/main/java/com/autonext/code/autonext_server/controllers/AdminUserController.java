package com.autonext.code.autonext_server.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.autonext.code.autonext_server.dto.UserForAdminDTO;
import com.autonext.code.autonext_server.exceptions.UserNotFoundException;
import com.autonext.code.autonext_server.services.UserManagementService;

@RestController
@RequestMapping("/api/admin/user")
public class AdminUserController {

    private final UserManagementService userManagementService;

    public AdminUserController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @GetMapping("/get-users")
    public ResponseEntity<?> getAllUsers(@RequestParam(required = false) String email) {
        if (email != null) {
            try {

                UserForAdminDTO userDto = userManagementService.getUserByEmail(email)
                        .orElseThrow(
                                () -> new UserNotFoundException("Usuario con email '" + email + "' no encontrado"));

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .body(userDto);

            } catch (UserNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
            }
        }
        return ResponseEntity.ok(userManagementService.getAllUsers());
    }

}
