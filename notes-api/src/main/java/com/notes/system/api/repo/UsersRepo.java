package com.notes.system.api.repo;

import com.notes.system.api.entity.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepo extends CrudRepository<Users, UUID> {

    Optional<Users> findByEmail(String email);

    boolean existsByEmail(String email);
}
