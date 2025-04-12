package io.github.vishvakalhara.handymanbackend.domains.dtos.tasks;

import io.github.vishvakalhara.handymanbackend.domains.dtos.categories.CategoryDTO;
import io.github.vishvakalhara.handymanbackend.domains.dtos.user.SimpleUserDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class SimpleTaskDTO {

    private UUID id;
    private String image;
    private SimpleUserDTO creator;
    private String title;
    private Double maxPrice;
    private Boolean isEmergency;
    private CategoryDTO category;
    private Boolean isDeleted;
    private LocalDateTime createdAt;
}
