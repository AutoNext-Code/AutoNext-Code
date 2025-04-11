package com.autonext.code.autonext_server.services;

import org.hibernate.StaleStateException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.dto.UserDto;
import com.autonext.code.autonext_server.exceptions.EmailNotConfirmedException;
import com.autonext.code.autonext_server.exceptions.UserNotFoundException;
import com.autonext.code.autonext_server.mapper.UserMapper;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;


  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {

    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;

  }

  
  private int getAuthenticatedUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Object principal = authentication.getPrincipal();

    if (principal instanceof User user) {
      return user.getId();
    }

    throw new SecurityException("Usuario no autenticado correctamente");
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

  @Transactional
  public void updatePassword(String password) {
    int userId = getAuthenticatedUserId();
    
    try {

      User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

      user.setPassword(passwordEncoder.encode(password));

      userRepository.save(user) ;
      
    } catch(StaleStateException sse) {
      throw new StaleStateException("Usuario no encontrado.") ;
    }

  }

}
