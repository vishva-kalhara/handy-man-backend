package io.github.vishvakalhara.handymanbackend.domains.dtos.tasks;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
@Builder
public class CreateTaskRequest {

    private MultipartFile image;

    @NotBlank(message = "Title is required!")
    private String title;

    @NotBlank(message = "Description is required!")
    private String description;

    @NotBlank(message = "Max Price is required!")
    private Double maxPrice;

    @NotBlank(message = "Select a category!")
    private UUID categoryId;

    private Boolean isEmergency;

    private UUID creatorId;
}
