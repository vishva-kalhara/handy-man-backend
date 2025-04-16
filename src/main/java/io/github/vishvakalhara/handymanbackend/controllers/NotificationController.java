package io.github.vishvakalhara.handymanbackend.controllers;

import io.github.vishvakalhara.handymanbackend.domains.dtos.notifications.NotificationDTO;
import io.github.vishvakalhara.handymanbackend.mappers.NotificationMapper;
import io.github.vishvakalhara.handymanbackend.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    private final NotificationMapper notificationMapper;

    @GetMapping("/me")
    public ResponseEntity<List<NotificationDTO>> getMyNotifications(
            @RequestAttribute UUID userId
    ) {

        return ResponseEntity.ok(
                notificationMapper.entityToDTO(
                        notificationService.getNotificationsOfUser(userId)
                )
        );
    }
}
