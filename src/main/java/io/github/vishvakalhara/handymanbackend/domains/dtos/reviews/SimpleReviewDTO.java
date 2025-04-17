package io.github.vishvakalhara.handymanbackend.domains.dtos.reviews;

import lombok.Data;

import java.util.UUID;

@Data
public class SimpleReviewDTO {

    private UUID id;
    private Double ratedValue;
    private String reviewText;
}
