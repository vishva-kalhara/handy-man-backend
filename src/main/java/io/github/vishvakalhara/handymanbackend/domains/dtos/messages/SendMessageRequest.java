package io.github.vishvakalhara.handymanbackend.domains.dtos.messages;

import io.github.vishvakalhara.handymanbackend.domains.MessageType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class SendMessageRequest {

    @NotNull(message = "Recipient id is required!")
    private UUID recipientId;

    @NotNull(message = "Message is required!")
    private String message;

    private MessageType messageType = MessageType.TEXT;
}