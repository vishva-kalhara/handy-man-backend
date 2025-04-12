package io.github.vishvakalhara.handymanbackend.domains.dtos.tasks;

import io.github.vishvakalhara.handymanbackend.domains.TaskStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class FilterTasksRequest {
    private String category;
    private Boolean isEmergency;
    private UUID creatorId;
    private TaskStatus taskStatus;
    private Double minPrice;
    private Double maxPrice;
    private Integer size;
    private Integer page;
}
