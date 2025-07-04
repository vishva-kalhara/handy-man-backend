package io.github.vishvakalhara.handymanbackend.repositories;

import io.github.vishvakalhara.handymanbackend.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {

    Optional<User> findUserByEmail(String email);

    boolean existsUserByEmail(String email);

    boolean existsUserById(UUID id);
}
