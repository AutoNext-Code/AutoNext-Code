package com.autonext.code.autonext_server.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

@Service
public class UserService {

  private final UserRepository userRepository;
  private final ValidationsFunctions validationsFunctions;

  public UserService(UserRepository userRepository, ValidationsFunctions validationsFunctions) {

    this.userRepository = userRepository;
    this.validationsFunctions = validationsFunctions;

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

  private int getAuthenticatedUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Object principal = authentication.getPrincipal();

    if (principal instanceof User user) {
      return user.getId();
    }

    throw new SecurityException("Usuario no autenticado correctamente");
  }

}
