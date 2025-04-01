package io.github.vishvakalhara.handymanbackend.domains.dtos.reviews;

import lombok.Data;

@Data
public class UpdateReviewRequest {

    private Double ratedValue;
    private String reviewText;

}