package io.github.vishvakalhara.handymanbackend.repositories;

import io.github.vishvakalhara.handymanbackend.domains.entities.Notification;
import io.github.vishvakalhara.handymanbackend.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, UUID> {

    List<Notification> getNotificationsByAssociatedUser_Id(UUID associatedUserId);

    Notification getNotificationsByAssociatedUserAndMessageEqualsIgnoreCase(User associatedUser, String message);
}
