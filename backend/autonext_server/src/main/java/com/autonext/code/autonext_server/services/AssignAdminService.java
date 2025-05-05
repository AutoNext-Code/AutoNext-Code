package com.autonext.code.autonext_server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.exceptions.AuthorizationException;
import com.autonext.code.autonext_server.exceptions.UserNotFoundException;
import com.autonext.code.autonext_server.repositories.UserRepository;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.models.enums.Role;

@Service
public class AssignAdminService {

    private final int ADMIN_ID = 1;

    @Autowired
    UserRepository userRepository;

    public void toggleAdminRole(int userId, int adminId) {
        try {
            if (adminId != ADMIN_ID || !userRepository.existsById(adminId)) {
                throw new AuthorizationException("No autorizado para realizar esta acción");
            }
            
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

            if (user.getRole() == Role.Admin) {
                user.setRole(Role.User);
            } else if(user.getRole() == Role.User) {
                user.setRole(Role.Admin);
            }

            userRepository.save(user);
        } catch (AuthorizationException e) {
            throw e;
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
