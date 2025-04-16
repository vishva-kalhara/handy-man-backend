package io.github.vishvakalhara.handymanbackend.services;

import io.github.vishvakalhara.handymanbackend.domains.entities.Notification;

import java.util.List;
import java.util.UUID;

public interface NotificationService {

    Notification AddNotification(Notification notification);

    List<Notification> AddNotification(List<Notification> notifications);

    List<Notification> getNotificationsOfUser(UUID userId);
}
