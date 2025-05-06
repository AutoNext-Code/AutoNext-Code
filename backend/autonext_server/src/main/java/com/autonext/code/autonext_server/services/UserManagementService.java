package com.autonext.code.autonext_server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.dto.UserForAdminDTO;
import com.autonext.code.autonext_server.exceptions.UserNotFoundException;
import com.autonext.code.autonext_server.mapper.UserMapper;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.models.WorkCenter;
import com.autonext.code.autonext_server.repositories.UserRepository;
import com.autonext.code.autonext_server.repositories.WorkCenterRepository;

@Service
public class UserManagementService {

    private final UserRepository userRepository;

    private final WorkCenterRepository workCenterRepository;

    private final UserMapper userMapper;

    public UserManagementService(UserRepository userRepository, WorkCenterRepository workCenterRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.workCenterRepository = workCenterRepository;
        this.userMapper = userMapper;
    }

    public List<UserForAdminDTO> getAllUsers() {
        return userMapper.convertToIterableUserForAdminDTO(userRepository.findAll());
    }

    public Optional<UserForAdminDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::convertToUserForAdminDTO);
    }

    public void setJobPosition(int userId, String jobPosition) {
        if (jobPosition == null) {
            throw new IllegalArgumentException("El puesto de trabajo no puede ser null");
        }
    
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

            user.setJobPosition(jobPosition);
    
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el puesto de trabajo", e);
        }
    }
    
    public void setWorkCenter(int userId, int workCenterId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

            if (workCenterId != 0) {
                WorkCenter workCenter = workCenterRepository.findById(workCenterId).orElseThrow(() -> new NullPointerException("WorkCenter no encontrado"));

                user.setWorkCenter(workCenter);
            } else {
                user.setWorkCenter(null);
            }
    
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el puesto de trabajo", e);
        }
    }

}
