package io.github.vishvakalhara.handymanbackend.mappers;

import io.github.vishvakalhara.handymanbackend.domains.dtos.categories.CategoryDTO;
import io.github.vishvakalhara.handymanbackend.domains.dtos.categories.CreateCategoryRequest;
import io.github.vishvakalhara.handymanbackend.domains.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    CategoryDTO toDTO(Category category);

    Category createCategoryRequestToEntity(CreateCategoryRequest createCategoryRequest);
}
