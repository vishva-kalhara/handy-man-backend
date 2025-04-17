package io.github.vishvakalhara.handymanbackend.services;

import io.github.vishvakalhara.handymanbackend.domains.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserService {

    User getOneUser(UUID id);

    User updateMyBio(String bio, UUID userId);

    User updateMyPicture(MultipartFile picture, UUID userId);
}
