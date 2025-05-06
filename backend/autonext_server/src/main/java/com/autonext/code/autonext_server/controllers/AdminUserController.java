package com.autonext.code.autonext_server.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.autonext.code.autonext_server.dto.UserForAdminDTO;
import com.autonext.code.autonext_server.exceptions.AuthorizationException;
import com.autonext.code.autonext_server.exceptions.UserNotFoundException;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.models.enums.JobPosition;
import com.autonext.code.autonext_server.services.AssignAdminService;
import com.autonext.code.autonext_server.services.UserManagementService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/admin/user")
public class AdminUserController {

    private final UserManagementService userManagementService;

    private final AssignAdminService assignAdminService;

    public AdminUserController(UserManagementService userManagementService, AssignAdminService assignAdminService) {
        this.userManagementService = userManagementService;
        this.assignAdminService = assignAdminService;
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

    @PutMapping("/update-job-position/{userId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> updateJobPosition(@PathVariable int userId, @RequestParam JobPosition jobPosition) {
        try {
            userManagementService.setJobPosition(userId, jobPosition);
            return ResponseEntity.ok("Puesto de trabajo actualizado correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    @PutMapping("/toggle-admin-role/{userId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> toggleAdminRole(@PathVariable int userId) {
        try {

            UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                    .getContext().getAuthentication();

            User user = (User) authentication.getPrincipal();

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            assignAdminService.toggleAdminRole(userId, user.getId());
            return ResponseEntity.ok("Rol de administrador actualizado correctamente");
        } catch (AuthorizationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    @PutMapping("/update-work-center/{userId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> updateWorkCenter(@PathVariable int userId, @RequestParam int workCenterId) {
        try {
            userManagementService.setWorkCenter(userId, workCenterId);
            return ResponseEntity.ok("Centro de trabajo actualizado correctamente");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Centro de trabajo no encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

}
