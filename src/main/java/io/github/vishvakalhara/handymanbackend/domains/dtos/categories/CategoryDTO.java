package io.github.vishvakalhara.handymanbackend.domains.dtos.categories;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CategoryDTO {
    private UUID id;
    private String categoryName;
}
