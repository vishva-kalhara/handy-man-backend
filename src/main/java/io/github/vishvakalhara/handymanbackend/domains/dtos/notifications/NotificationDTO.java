package io.github.vishvakalhara.handymanbackend.domains.dtos.notifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {

    private LocalDateTime createdAt;
    private String message;
    private String buttonText;
    private String href;
}
