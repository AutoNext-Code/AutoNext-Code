package com.autonext.code.autonext_server.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.autonext.code.autonext_server.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
  
  Optional<User> findByEmail(String email);

  Optional<User> findByConfirmationToken(String confirmationToken);

  Optional<User> findByEmailAndEmailConfirm(String email, boolean emailConfirm);

  Optional<User> findById(int id);

  @EntityGraph(attributePaths = "workCenter")
  @Query("SELECT u FROM User u WHERE u.id = :id")
  Optional<User> findByIdWithWorkCenter(@Param("id") int id);

  @Modifying
  @Query("UPDATE User u SET u.password = :password WHERE u.id = :id")
  void updatePassword(@Param("password") String password, @Param("id") int id);
  
}
