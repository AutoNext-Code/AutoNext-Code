package com.autonext.code.autonext_server.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.autonext.code.autonext_server.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
  
  Optional<User> findByEmail(String email);

  Optional<User> findByConfirmationToken(String confirmationToken);

  Optional<User> findByEmailAndEmailConfirm(String email, boolean emailConfirm);

  @EntityGraph(attributePaths = "workCenter")
  Optional<User> findByIdWithWorkCenter(int id);

  
}
