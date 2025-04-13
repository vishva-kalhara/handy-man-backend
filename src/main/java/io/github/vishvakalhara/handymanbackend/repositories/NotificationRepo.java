package io.github.vishvakalhara.handymanbackend.repositories;

import io.github.vishvakalhara.handymanbackend.domains.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, UUID> {
}
