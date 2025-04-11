package com.autonext.code.autonext_server.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.dto.UserDto;
import com.autonext.code.autonext_server.exceptions.EmailNotConfirmedException;
import com.autonext.code.autonext_server.exceptions.UserNotFoundException;
import com.autonext.code.autonext_server.mapper.UserMapper;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.repositories.UserRepository;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {

    this.userRepository = userRepository;

  }

  public User getUserByEmail(String email) {
    return userRepository.findByEmailAndEmailConfirm(email, true)
        .orElseThrow(() -> new EmailNotConfirmedException("No has confirmado tu correo."));
  }

  public UserDto getProfile(int userId) {
    return userRepository.findByIdWithWorkCenter(userId)
        .map(UserMapper::toUserDto)
        .orElseThrow(() -> new UserNotFoundException("Usuario no existente"));
  }

  public UserDto editProfile(UserDto userDto) {
    User user = userRepository.findById(getAuthenticatedUserId())
        .orElseThrow(() -> new UserNotFoundException("Usuario no existente"));
    
    user.setName(userDto.getName());
    user.setSurname(userDto.getSurname());
    user.setEmail(userDto.getEmail());
    
    User updatedUser = userRepository.save(user);
    
    return UserMapper.toUserDto(updatedUser);
  }

  private int getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
 
        if (principal instanceof User user) {
        return user.getId();
        }
 
        throw new SecurityException("Usuario no autenticado correctamente");
    }

}
