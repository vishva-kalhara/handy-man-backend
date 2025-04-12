package io.github.vishvakalhara.handymanbackend.domains.dtos.tasks;

import io.github.vishvakalhara.handymanbackend.domains.TaskStatus;
import io.github.vishvakalhara.handymanbackend.domains.dtos.bids.BidDTO;
import io.github.vishvakalhara.handymanbackend.domains.dtos.categories.CategoryDTO;
import io.github.vishvakalhara.handymanbackend.domains.dtos.user.OnlyUserId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class TaskDTO {

    private UUID id;
    private String title;
    private String description;
    private String image;
    private Double maxPrice;
    private Boolean isEmergency;
    private String taskStatus;
    private LocalDateTime createdAt;
    private OnlyUserId creator;
    private CategoryDTO category;
    private List<BidDTO> bids = new ArrayList<>();
}
