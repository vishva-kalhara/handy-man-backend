package io.github.vishvakalhara.handymanbackend.domains.dtos.bids;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateBidRequest {

    @NotNull(message = "Price is required!")
//    @NotBlank(message = "Price is required!")
    @Positive(message = "Price must be a positive value!")
    private Double price;

    @NotNull(message = "Task Id is required!")
//    @NotBlank(message = "taskId is required!")
    private UUID taskId;

    // Mapping at the mapper
    private UUID bidderId;
}