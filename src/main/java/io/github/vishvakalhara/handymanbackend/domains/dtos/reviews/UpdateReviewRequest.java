package io.github.vishvakalhara.handymanbackend.domains.dtos.reviews;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateReviewRequest {

    private UUID reviewId;
    private Double ratedValue;
    private String reviewText;

}