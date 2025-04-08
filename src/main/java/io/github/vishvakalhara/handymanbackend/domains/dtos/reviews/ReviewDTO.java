package io.github.vishvakalhara.handymanbackend.domains.dtos.reviews;

import io.github.vishvakalhara.handymanbackend.domains.entities.User;
import lombok.Data;

import java.util.UUID;

@Data
public class ReviewDTO {
    private UUID id;
    private Double ratedValue;
    private String reviewText;
    private User reviewedBy;

}
