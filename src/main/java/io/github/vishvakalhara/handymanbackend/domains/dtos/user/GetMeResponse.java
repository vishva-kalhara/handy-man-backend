package io.github.vishvakalhara.handymanbackend.domains.dtos.user;

import io.github.vishvakalhara.handymanbackend.domains.dtos.tasks.TaskDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetMeResponse extends GetUserResponse {
    private String displayName;
    private String email;
    private String bio;
    private String profileImage;
    private Double avgRating;
    private List<TaskDTO> tasks = new ArrayList<>();

}
