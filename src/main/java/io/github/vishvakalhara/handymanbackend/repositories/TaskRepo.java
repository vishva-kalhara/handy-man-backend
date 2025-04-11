package io.github.vishvakalhara.handymanbackend.repositories;

import io.github.vishvakalhara.handymanbackend.domains.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRepo extends JpaRepository<Task, UUID> {
}
