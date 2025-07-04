package io.github.vishvakalhara.handymanbackend.controllers;

import io.github.vishvakalhara.handymanbackend.domains.dtos.messages.MessageDTO;
import io.github.vishvakalhara.handymanbackend.domains.dtos.messages.SendMessageRequest;
import io.github.vishvakalhara.handymanbackend.domains.dtos.user.SimpleUserDTO;
import io.github.vishvakalhara.handymanbackend.domains.entities.Message;
import io.github.vishvakalhara.handymanbackend.domains.entities.User;
import io.github.vishvakalhara.handymanbackend.mappers.MessageMapper;
import io.github.vishvakalhara.handymanbackend.mappers.UserMapper;
import io.github.vishvakalhara.handymanbackend.services.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    private final MessageMapper messageMapper;

    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<MessageDTO> sendMessage(
            @Valid @RequestBody SendMessageRequest requestBody,
            @RequestAttribute UUID userId
    ) {

        Message sentMessage = messageService.sendMessage(userId, requestBody);
        return new ResponseEntity<>(messageMapper.entityToDTO(sentMessage, userId), HttpStatus.CREATED);
    }

    @GetMapping("/by-recipient/{recipientId}")
    public ResponseEntity<List<MessageDTO>> getAllMessagesByRecipient(
            @PathVariable UUID recipientId,
            @RequestAttribute UUID userId
    ) {

        List<Message> messages = messageService.getMessagesByRecipient(recipientId, userId);
        return ResponseEntity.ok(messageMapper.entityToDTO(messages, userId));
    }

    @GetMapping("/my-recipients")
    public ResponseEntity<List<SimpleUserDTO>> getMyRecipients(@RequestAttribute UUID userId){

        List<User> users = messageService.getMyRecipients(userId);
        return ResponseEntity.ok(userMapper.entityToSimpleUserDTO(users));
    }
}