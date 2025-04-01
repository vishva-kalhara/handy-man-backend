package io.github.vishvakalhara.handymanbackend.controllers;

import io.github.vishvakalhara.handymanbackend.domains.dtos.messages.MessageDTO;
import io.github.vishvakalhara.handymanbackend.domains.dtos.messages.SendMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    @PostMapping
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody SendMessageRequest requestBody){

        // Get sender's id using request scope
        return new ResponseEntity<>(new MessageDTO(), HttpStatus.CREATED);
    }

    @GetMapping("/byRecipient/{recipientId}")
    public ResponseEntity<List<MessageDTO>> getAllMessagesByRecipient(@PathVariable UUID recipientId){

        // Filter messages by the logged user id and recipient id. these can be sender or recipient.
        return ResponseEntity.ok(new ArrayList<>());
    }
}