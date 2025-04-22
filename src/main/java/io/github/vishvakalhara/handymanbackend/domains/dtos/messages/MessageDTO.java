package io.github.vishvakalhara.handymanbackend.domains.dtos.messages;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MessageDTO {

    private UUID id;
    private String message;
    private LocalDateTime sentAt;
    private String messageType;
    private Boolean isSentByMe;
}