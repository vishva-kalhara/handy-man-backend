package io.github.vishvakalhara.handymanbackend.domains.dtos.messages;

import lombok.Data;

import java.util.UUID;

@Data
public class SendMessageRequest {

    private String message;
    private UUID recipient;
}