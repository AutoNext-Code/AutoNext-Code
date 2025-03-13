package com.autonext.code.autonext_server.repositories;
import org.springframework.data.repository.CrudRepository;

import com.autonext.code.autonext_server.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {
}
