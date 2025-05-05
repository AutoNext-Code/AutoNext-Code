package com.autonext.code.autonext_server.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.dto.UserForAdminDTO;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.repositories.UserRepository;

@Service
public class UserManagementService {

    private final UserRepository userRepository;

    public UserManagementService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserForAdminDTO> getAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserForAdminDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::convertToDTO);
    }
    
    private UserForAdminDTO convertToDTO(User user) {
        return new UserForAdminDTO(
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getStrikes().size(),
                user.getRole(),
                user.getJobPosition(),
                user.getWorkCenter() != null ? user.getWorkCenter().getName() : null);
    }

}
