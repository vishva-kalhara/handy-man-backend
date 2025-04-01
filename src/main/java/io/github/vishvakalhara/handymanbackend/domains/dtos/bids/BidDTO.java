package io.github.vishvakalhara.handymanbackend.domains.dtos.bids;

import io.github.vishvakalhara.handymanbackend.domains.BidStatus;
import io.github.vishvakalhara.handymanbackend.domains.entities.Task;
import io.github.vishvakalhara.handymanbackend.domains.entities.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BidDTO {

    private UUID id;
    private Double price;
    private BidStatus bidStatus;
    private LocalDateTime createdAt;
    private Task associatedTask;
    private User bidder;

}
