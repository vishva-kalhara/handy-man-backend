package io.github.vishvakalhara.handymanbackend.domains.dtos.bids;

import io.github.vishvakalhara.handymanbackend.domains.entities.Task;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateBidRequest {

    private Double price;
    private UUID taskId;
}