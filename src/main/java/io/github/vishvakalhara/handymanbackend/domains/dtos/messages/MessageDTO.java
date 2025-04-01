package io.github.vishvakalhara.handymanbackend.domains.dtos.messages;

import io.github.vishvakalhara.handymanbackend.domains.entities.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MessageDTO {

    private UUID id;
    private String message;
    private LocalDateTime sentAt;
    private User sender;
    private User recipient;
}