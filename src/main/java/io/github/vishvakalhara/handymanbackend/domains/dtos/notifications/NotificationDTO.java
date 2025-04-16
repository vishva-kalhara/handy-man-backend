package io.github.vishvakalhara.handymanbackend.domains.dtos.notifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {

    private UUID id;
    private String title;
    private String message;
    private String href;
    private LocalDateTime createAt;
    private boolean hasNoted;
}
