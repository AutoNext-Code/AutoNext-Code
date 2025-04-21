package com.autonext.code.autonext_server.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.hibernate.StaleStateException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.dto.UserDto;
import com.autonext.code.autonext_server.dto.UserRequestDTO;
import com.autonext.code.autonext_server.exceptions.EmailNotConfirmedException;
import com.autonext.code.autonext_server.exceptions.NoChangesMadeException;
import com.autonext.code.autonext_server.exceptions.UserNotFoundException;
import com.autonext.code.autonext_server.mapper.UserMapper;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.repositories.UserRepository;
import com.autonext.code.autonext_server.validations.ValidationsFunctions;

import jakarta.transaction.Transactional;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final ValidationsFunctions validationsFunctions;

  public UserService(UserRepository userRepository, ValidationsFunctions validationsFunctions, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.validationsFunctions = validationsFunctions;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
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

  public UserDto editProfile(UserRequestDTO userRequestDto) {
    User user = userRepository.findById(getAuthenticatedUserId())
        .orElseThrow(() -> new UserNotFoundException("Usuario no existente"));

    if (!validationsFunctions.isValidEmail(userRequestDto.getEmail())) {
      throw new RuntimeException("El formato de email es incorrecto");
    }

    if (!hasChanges(user, userRequestDto)) {
      throw new NoChangesMadeException("No se han realizado cambios en los datos del usuario");
    }

    user.setName(userRequestDto.getName());
    user.setSurname(userRequestDto.getSurname());
    user.setEmail(userRequestDto.getEmail());

    User updatedUser = userRepository.save(user);

    return UserMapper.toUserDto(updatedUser);
  }

  private boolean hasChanges(User user, UserRequestDTO userRequestDto) {
    return !user.getEmail().equals(userRequestDto.getEmail())
        || !user.getName().equals(userRequestDto.getName())
        || !user.getSurname().equals(userRequestDto.getSurname());
  }

  @Transactional
  public void updatePassword(String newPassword, String oldPassword) {
    int userId = getAuthenticatedUserId();
    
    try {

      User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), oldPassword)) ;

      user.setPassword(passwordEncoder.encode(newPassword));

      userRepository.save(user) ;
      
    } catch(StaleStateException sse) {
      throw new StaleStateException("Usuario no encontrado.") ;
    }
  }
  

}
