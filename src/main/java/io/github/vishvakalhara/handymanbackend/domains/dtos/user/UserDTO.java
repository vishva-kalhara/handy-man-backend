package io.github.vishvakalhara.handymanbackend.domains.dtos.user;

import io.github.vishvakalhara.handymanbackend.domains.dtos.tasks.TaskDTO;
import io.github.vishvakalhara.handymanbackend.domains.entities.Review;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class UserDTO {

    private UUID id;
    private String displayName;
    private String profileImage;
    private String email;
    private String bio;
    private Double avgRating;
//    private List<Review> reviewsIGot = new ArrayList<>();
//    private List<TaskDTO> tasks = new ArrayList<>();

}