package io.github.vishvakalhara.handymanbackend.domains.dtos.categories;

import lombok.Data;

import java.util.UUID;

@Data
public class CategoryDTO {
    private UUID id;
    private String categoryName;
}
