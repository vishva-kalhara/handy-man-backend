package io.github.vishvakalhara.handymanbackend.services;

import io.github.vishvakalhara.handymanbackend.domains.entities.User;

import java.util.UUID;

public interface UserService {

    User getOneUser(UUID id);

    User updateMyBio(String bio, UUID userId);
}
