package io.github.vishvakalhara.handymanbackend.controllers;

import io.github.vishvakalhara.handymanbackend.domains.dtos.user.UpdateMyBioRequest;
import io.github.vishvakalhara.handymanbackend.domains.dtos.user.UserDTO;
import io.github.vishvakalhara.handymanbackend.domains.entities.User;
import io.github.vishvakalhara.handymanbackend.mappers.UserMapper;
import io.github.vishvakalhara.handymanbackend.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getOneUser(@PathVariable UUID id) {

        User user = userService.getOneUser(id);
        return ResponseEntity.ok(userMapper.entityToGetMe(user));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMe(@RequestAttribute UUID userId) {

        User user = userService.getOneUser(userId);
        return ResponseEntity.ok(userMapper.entityToGetMe(user));
    }

    @PatchMapping(path = "/me/bio")
    public ResponseEntity<UserDTO> updateMe(
            @Valid @RequestBody UpdateMyBioRequest requestBody,
            @RequestAttribute UUID userId
    ) {

        User user = userService.updateMyBio(requestBody.getBio(), userId);
        return ResponseEntity.ok(userMapper.entityToGetMe(user));
    }

    @PatchMapping(path = "/me/my-img", consumes = "multipart/form-data")
    public ResponseEntity<UserDTO> updateMyImage(
            @RequestPart("image") MultipartFile image,
            @RequestAttribute UUID userId
    ) {

        User user = userService.updateMyPicture(image, userId);
        return ResponseEntity.ok(userMapper.entityToGetMe(user));
    }
}