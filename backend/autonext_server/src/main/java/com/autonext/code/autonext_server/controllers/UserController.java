package com.autonext.code.autonext_server.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.autonext.code.autonext_server.dto.UserDto;
import com.autonext.code.autonext_server.exceptions.UserNotFoundException;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.services.UserService;

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
    public ResponseEntity<UserDto> editProfile(@RequestBody UserDto userDto) {
        try {
            UserDto updatedUser = userService.editProfile(userDto);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
