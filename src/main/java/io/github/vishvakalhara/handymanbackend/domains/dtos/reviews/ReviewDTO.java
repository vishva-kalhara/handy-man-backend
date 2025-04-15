package io.github.vishvakalhara.handymanbackend.domains.dtos.reviews;

import io.github.vishvakalhara.handymanbackend.domains.dtos.user.SimpleUserDTO;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ReviewDTO {
    private UUID id;
    private Double ratedValue;
    private String reviewText;
    private SimpleUserDTO reviewedBy;
    private SimpleUserDTO reviewGot;
    private String reviewGotAsRole;
}
