package org.ayd.apimecahnicalworkshop.repositories;

import org.ayd.apimecahnicalworkshop.dto.userdto.UserDTO;
import org.ayd.apimecahnicalworkshop.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByUsername(String username);
    User findByUsername(String username);
    User findByEmail(String email);

    List<User> getUserByIsActive(boolean active);
}
