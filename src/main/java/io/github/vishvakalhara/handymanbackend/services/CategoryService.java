package io.github.vishvakalhara.handymanbackend.services;

import io.github.vishvakalhara.handymanbackend.domains.entities.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category createCategory(Category category);
}
