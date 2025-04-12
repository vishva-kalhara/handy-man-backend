package io.github.vishvakalhara.handymanbackend.domains.dtos.user;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SimpleUserDTO {
    private UUID id;
    private String displayName;
    private String profileImage;
    private Double avgRating;
}
