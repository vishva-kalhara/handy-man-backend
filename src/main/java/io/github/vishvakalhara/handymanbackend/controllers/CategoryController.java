package io.github.vishvakalhara.handymanbackend.controllers;

import io.github.vishvakalhara.handymanbackend.domains.dtos.categories.CategoryDTO;
import io.github.vishvakalhara.handymanbackend.domains.dtos.categories.CreateCategoryRequest;
import io.github.vishvakalhara.handymanbackend.domains.entities.Category;
import io.github.vishvakalhara.handymanbackend.mappers.CategoryMapper;
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

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {

        List<Category> categories = categoryService.getAllCategories();
        List<CategoryDTO> categoryDTOs = categories.stream().map(categoryMapper::toDTO).toList();

        return ResponseEntity.ok(categoryDTOs);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CreateCategoryRequest requestBody) {

        Category categoryToBeCreated = categoryMapper.createCategoryRequestToEntity(requestBody);

        Category createdCategory = categoryService.createCategory(categoryToBeCreated);

        return new ResponseEntity<>(categoryMapper.toDTO(createdCategory), HttpStatus.CREATED);
    }

    @Deprecated
//    @PostMapping(path = "/seed")
    public ResponseEntity<String> seedCategories() {

        String[] categories = new String[]{
                "Plumbing",
                "Electrical Work",
                "Carpentry",
                "Painting",
                "Drywall Repair",
                "Roofing",
                "Gutter Cleaning",
                "Window Repair",
                "Door Installation",
                "Flooring",

                // Outdoor & Garden
                "Landscaping",
                "Fence Repair",
                "Deck Building",
                "Pressure Washing",
                "Tree Trimming",
                "Snow Removal",
                "Patio Installation",

                // Cleaning & Organization
                "Deep Cleaning",
                "Move-In/Move-Out Cleaning",
                "Decluttering",
                "Carpet Cleaning",
                "Junk Removal",

                // Appliance & Tech
                "Appliance Repair",
                "Furniture Assembly",
                "TV Mounting",
                "Smart Home Setup",

                // Automotive
                "Car Detailing",
                "Basic Auto Repair",

                // Specialty Services
                "Event Setup",
                "Light Demolition",
                "Pet-Friendly Repairs",
                "Elderly Home Modifications",
                "Emergency Repairs",
                "Custom Fabrication",

                // Seasonal
                "Holiday Decor Setup",
                "AC Tune-Up",
                "Heater Maintenance",

                // Miscellaneous
                "General Labor",
                "Odd Jobs"
        };

        for (String name : categories) {

            Category category = new Category();
            category.setCategoryName(name);

            categoryService.createCategory(category);

        }

        return ResponseEntity.ok("Seeded");
    }
}
