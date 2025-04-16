package io.github.vishvakalhara.handymanbackend.services;

import io.github.vishvakalhara.handymanbackend.domains.entities.Notification;
import io.github.vishvakalhara.handymanbackend.domains.entities.User;

import java.util.List;
import java.util.UUID;

public interface NotificationService {

    Notification AddNotification(String title, String message, String href, UUID userId);

    Notification AddNotification(String title, String message, String href, User user);

    Notification AddNotification(String title, String message, String href, boolean isActionRequired, User user);

    Notification AddNotification(Notification notification);

    List<Notification> AddNotification(List<Notification> notifications);

    List<Notification> getNotificationsOfUser(UUID userId);
}
