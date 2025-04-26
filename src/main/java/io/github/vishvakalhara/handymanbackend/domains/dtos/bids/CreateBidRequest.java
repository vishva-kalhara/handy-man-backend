package io.github.vishvakalhara.handymanbackend.domains.dtos.bids;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CreateBidRequest {

    @NotNull(message = "Price is required!")
    @Positive(message = "Price must be a positive value!")
    private Double price;

    @NotNull(message = "Task Id is required!")
    private UUID taskId;

    // Mapping at the mapper
    private UUID bidderId;
}