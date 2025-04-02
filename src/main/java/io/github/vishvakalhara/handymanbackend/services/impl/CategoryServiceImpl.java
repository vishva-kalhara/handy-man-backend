package io.github.vishvakalhara.handymanbackend.services.impl;

import io.github.vishvakalhara.handymanbackend.domains.entities.Category;
import io.github.vishvakalhara.handymanbackend.repositories.CategoryRepo;
import io.github.vishvakalhara.handymanbackend.services.CategoryService;
import io.github.vishvakalhara.handymanbackend.error_handling.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category createCategory(Category category) {

        if(categoryRepo.existsCategoryByCategoryNameIgnoreCase(category.getCategoryName())){
            throw new AppException("There is already a category with the same name");
        }

        return categoryRepo.save(category);
    }
}
