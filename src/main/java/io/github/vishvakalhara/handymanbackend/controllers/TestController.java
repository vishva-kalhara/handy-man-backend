package io.github.vishvakalhara.handymanbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("")
@RequiredArgsConstructor
public class TestController {

    @GetMapping
    public ResponseEntity<String> testApp(){
        return ResponseEntity.ok("Server is up and running...");
    }
}
