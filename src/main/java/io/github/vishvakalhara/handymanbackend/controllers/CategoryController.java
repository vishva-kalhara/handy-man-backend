package io.github.vishvakalhara.handymanbackend.controllers;

import io.github.vishvakalhara.handymanbackend.domains.dtos.categories.CategoryDTO;
import io.github.vishvakalhara.handymanbackend.domains.dtos.categories.CreateCategoryRequest;
import io.github.vishvakalhara.handymanbackend.domains.entities.Category;
import io.github.vishvakalhara.handymanbackend.mappers.CategoryMapper;
import io.github.vishvakalhara.handymanbackend.repositories.CategoryRepo;
import io.github.vishvakalhara.handymanbackend.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;
    private final CategoryRepo categoryRepo;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories (){

        List<Category> categories = categoryService.getAllCategories();
        List<CategoryDTO> categoryDTOs = categories.stream().map(categoryMapper::toDTO).toList();

        return ResponseEntity.ok(categoryDTOs);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CreateCategoryRequest requestBody){

        Category categoryToBeCreated = categoryMapper.createCategoryRequestToEntity(requestBody);

        Category createdCategory = categoryService.createCategory(categoryToBeCreated);

        return new ResponseEntity<>(categoryMapper.toDTO(createdCategory), HttpStatus.CREATED);
    }
}
