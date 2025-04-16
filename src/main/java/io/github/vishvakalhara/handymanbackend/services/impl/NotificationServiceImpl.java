package io.github.vishvakalhara.handymanbackend.services.impl;

import io.github.vishvakalhara.handymanbackend.domains.entities.Notification;
import io.github.vishvakalhara.handymanbackend.domains.entities.User;
import io.github.vishvakalhara.handymanbackend.error_handling.AppException;
import io.github.vishvakalhara.handymanbackend.repositories.NotificationRepo;
import io.github.vishvakalhara.handymanbackend.repositories.UserRepo;
import io.github.vishvakalhara.handymanbackend.services.NotificationService;
import io.github.vishvakalhara.handymanbackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepo notificationRepo;

    private final UserService userService;

    private final UserRepo userRepo;

    @Override
    public Notification AddNotification(String title, String message, String href, UUID userId) {

        return this.AddNotification(title, message, href, userService.getOneUser(userId));
    }

    @Override
    public Notification AddNotification(String title, String message, String href, User user) {

        return this.AddNotification(title, message, href, false, new User());
    }

    @Override
    public Notification AddNotification(String title, String message, String href, boolean isActionRequired, User user) {

        return this.AddNotification(Notification.builder()
                .title(title)
                .message(message)
                .href(href)
                .associatedUser(user)
                .hasNoted(isActionRequired)
                .build()
        );
    }

    @Override
    public Notification AddNotification(Notification notification) {

        return notificationRepo.save(notification);
    }

    @Transactional
    @Override
    public List<Notification> AddNotification(List<Notification> notifications) {

        return notificationRepo.saveAll(notifications);
    }

    @Override
    public List<Notification> getNotificationsOfUser(UUID userId) {

        if(!userRepo.existsUserById(userId)) {
            throw new AppException("User Not Found!", HttpStatus.NOT_FOUND);
        }

        return notificationRepo.getNotificationsByAssociatedUser_Id(userId);
    }
}
