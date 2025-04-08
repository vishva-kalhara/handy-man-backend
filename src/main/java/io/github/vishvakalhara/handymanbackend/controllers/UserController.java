package io.github.vishvakalhara.handymanbackend.controllers;

import io.github.vishvakalhara.handymanbackend.domains.dtos.user.GetMeResponse;
import io.github.vishvakalhara.handymanbackend.domains.dtos.user.GetUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/users")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getOneUser(@PathVariable UUID id){

        return ResponseEntity.ok(new GetUserResponse());
    }

    @GetMapping("/me")
    public ResponseEntity<GetMeResponse> getMe(){

        // Get UUID from request scope
        return ResponseEntity.ok(new GetMeResponse());
    }

    @PatchMapping(path = "/me")
    public ResponseEntity<GetMeResponse> updateMe(@RequestPart("bio") String bio){

        // Get UUID from request scope
        return ResponseEntity.ok(new GetMeResponse());
    }

    @PatchMapping(path = "/my-img", consumes = "multipart/form-data")
    public ResponseEntity<GetMeResponse> updateMyImage(
            @RequestPart("image") MultipartFile image){

        // Get UUID from request scope
        return ResponseEntity.ok(new GetMeResponse());
    }
}