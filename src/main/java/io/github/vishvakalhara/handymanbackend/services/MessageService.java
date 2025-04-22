package io.github.vishvakalhara.handymanbackend.services;

import io.github.vishvakalhara.handymanbackend.domains.dtos.messages.SendMessageRequest;
import io.github.vishvakalhara.handymanbackend.domains.entities.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    Message sendMessage(UUID senderId, SendMessageRequest reqBody);

    List<Message> getMessagesByRecipient(UUID recipientId, UUID myId);
}
