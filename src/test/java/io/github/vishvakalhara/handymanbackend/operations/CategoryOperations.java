package io.github.vishvakalhara.handymanbackend.operations;

import io.github.vishvakalhara.handymanbackend.domains.entities.Category;
import io.github.vishvakalhara.handymanbackend.repositories.CategoryRepo;

import java.util.UUID;

public class CategoryOperations {

    public static synchronized UUID createMockCategory(CategoryRepo categoryRepo){
        Category category = categoryRepo.save(
                Category.builder()
                        .categoryName("Test Category")
                        .build()
        );

        return category.getId();
    }
}
