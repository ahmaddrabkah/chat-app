package dev.ahmad.authenticationservice.repository;

import dev.ahmad.authenticationservice.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String roleName);
}
