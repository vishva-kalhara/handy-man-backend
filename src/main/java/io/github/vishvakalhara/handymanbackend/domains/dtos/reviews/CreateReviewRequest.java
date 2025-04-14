package io.github.vishvakalhara.handymanbackend.domains.dtos.reviews;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateReviewRequest {

    @NotNull(message = "Rated Value is required!")
    @Min(value = 1, message = "Minimum rating value is 1!")
    @Max(value = 5, message = "Maximum rating value is 5!")
    private Double ratedValue;

    @NotNull(message = "Review Text is required!")
    private String reviewText;

    @NotNull(message = "ReviewedToId is required!")
    private UUID reviewedToId;

    @NotNull(message = "Task Id is required!")
    private UUID taskId;
}