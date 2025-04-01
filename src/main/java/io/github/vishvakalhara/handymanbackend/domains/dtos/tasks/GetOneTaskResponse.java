package io.github.vishvakalhara.handymanbackend.domains.dtos.tasks;

import io.github.vishvakalhara.handymanbackend.domains.entities.Bid;
import io.github.vishvakalhara.handymanbackend.domains.entities.Category;
import io.github.vishvakalhara.handymanbackend.domains.entities.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class GetOneTaskResponse {

    private UUID id;
    private String title;
    private String description;
    private String image;
    private Double maxPrice;
    private Boolean isEmergency;
    private Boolean isCompleted;
    private LocalDateTime createdAt;
    private User creator;
    private Category category;
    private List<Bid> bids = new ArrayList<>();

}
