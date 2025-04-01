package io.github.vishvakalhara.handymanbackend.domains.dtos.user;

import io.github.vishvakalhara.handymanbackend.domains.entities.Review;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class GetUserResponse {

    private UUID id;
    private String displayName;
    private String profileImage;
    private String bio;
    private Double myRating;
    private List<Review> reviewsIGot;

}