package io.github.vishvakalhara.handymanbackend.domains.dtos.messages;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class SendMessageRequest {

    @NotBlank(message = "Recipient id is required!")
    private UUID recipient;

    @NotBlank(message = "Message is required!")
    private String message;
}