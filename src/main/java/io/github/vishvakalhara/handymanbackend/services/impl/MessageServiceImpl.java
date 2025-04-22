package io.github.vishvakalhara.handymanbackend.services.impl;

import io.github.vishvakalhara.handymanbackend.domains.dtos.messages.SendMessageRequest;
import io.github.vishvakalhara.handymanbackend.domains.entities.Message;
import io.github.vishvakalhara.handymanbackend.domains.entities.User;
import io.github.vishvakalhara.handymanbackend.error_handling.AppException;
import io.github.vishvakalhara.handymanbackend.repositories.MessageRepo;
import io.github.vishvakalhara.handymanbackend.repositories.UserRepo;
import io.github.vishvakalhara.handymanbackend.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepo messageRepo;

    private final UserRepo userRepo;

    @Override
    public Message sendMessage(UUID senderId, SendMessageRequest reqBody) {

        if(senderId.equals(reqBody.getRecipientId())){
            throw new AppException("Sender cannot send messages back to sender!", HttpStatus.FORBIDDEN);
        }

        User sender = userRepo.findById(senderId).orElseThrow(() ->
                new AppException("Sender not found!", HttpStatus.NOT_FOUND)
        );

        User recipient = userRepo.findById(reqBody.getRecipientId()).orElseThrow(() ->
                new AppException("Receiver not found!", HttpStatus.NOT_FOUND)
        );

        Message message = Message.builder()
                .message(reqBody.getMessage())
                .messageType(reqBody.getMessageType())
                .recipient(recipient)
                .sender(sender)
                .build();

        return messageRepo.save(message);
    }

    @Override
    public List<Message> getMessagesByRecipient(UUID recipientId, UUID myId) {

        if(recipientId.equals(myId)){
            throw new AppException("Sender cannot send messages back to sender!", HttpStatus.FORBIDDEN);
        }

        if(!userRepo.existsUserById(recipientId)) {
            throw new AppException("Recipient not found!", HttpStatus.NOT_FOUND);
        }

        if(!userRepo.existsUserById(myId)) {
            throw new AppException("User not found!", HttpStatus.NOT_FOUND);
        }

        return messageRepo.findMessagesByReceiver(recipientId, myId);
    }

    @Override
    public List<User> getMyRecipients(UUID myId) {

        if(!userRepo.existsUserById(myId)) {
            throw new AppException("User not found!", HttpStatus.NOT_FOUND);
        }

        return messageRepo.findDistinctUsersCommunicatedWith(myId);
    }
}
