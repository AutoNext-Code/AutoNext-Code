package com.autonext.code.autonext_server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.dto.UserForAdminDTO;
import com.autonext.code.autonext_server.mapper.UserMapper;
import com.autonext.code.autonext_server.repositories.UserRepository;

@Service
public class UserManagementService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserManagementService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserForAdminDTO> getAllUsers() {
        return userMapper.convertToIterableUserForAdminDTO(userRepository.findAll());
    }

    public Optional<UserForAdminDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::convertToUserForAdminDTO);
    }
}
