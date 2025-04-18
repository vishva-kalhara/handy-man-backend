package io.github.vishvakalhara.handymanbackend.services.impl;

import io.github.vishvakalhara.handymanbackend.aws_s3_storage.ResourceType;
import io.github.vishvakalhara.handymanbackend.aws_s3_storage.S3Service;
import io.github.vishvakalhara.handymanbackend.domains.entities.Notification;
import io.github.vishvakalhara.handymanbackend.domains.entities.User;
import io.github.vishvakalhara.handymanbackend.error_handling.AppException;
import io.github.vishvakalhara.handymanbackend.repositories.NotificationRepo;
import io.github.vishvakalhara.handymanbackend.repositories.UserRepo;
import io.github.vishvakalhara.handymanbackend.security.UserPrincipal;
import io.github.vishvakalhara.handymanbackend.services.UserService;
import io.github.vishvakalhara.handymanbackend.util.NotificationText;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;

    private final NotificationRepo notificationRepo;

    private final S3Service s3Service;

    @Override
    public User getOneUser(UUID id) {
        return userRepo.findById(id).orElseThrow(() -> new AppException("No User found!", HttpStatus.NOT_FOUND));
    }

    @Transactional
    @Override
    public User updateMyBio(String bio, UUID userId) {

        User user = userRepo.findById(userId).orElseThrow(
                () -> new AppException("User not found!", HttpStatus.NOT_FOUND)
        );

        Notification notification = notificationRepo.getNotificationsByAssociatedUserAndMessageEqualsIgnoreCase(
                user,
                NotificationText.getMessageToUserAtRegistration()
        );

        if(notification != null) {
            notificationRepo.delete(notification);
        }

        user.setBio(bio);
        return userRepo.save(user);
    }

    @Override
    public User updateMyPicture(MultipartFile picture, UUID userId) {

        User user = userRepo.findById(userId).orElseThrow(
                () -> new AppException("User not found!", HttpStatus.NOT_FOUND)
        );

        String uri = s3Service.uploadFile(picture, ResourceType.USER_IMAGE);
        user.setProfileImage(uri);

        return userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepo.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found! please register."));

        return new UserPrincipal(user);
    }
}
