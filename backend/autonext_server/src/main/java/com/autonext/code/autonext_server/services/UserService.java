package com.autonext.code.autonext_server.services;

import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.exceptions.EmailNotConfirmedException;
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

}
