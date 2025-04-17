package io.github.vishvakalhara.handymanbackend.mappers;

import io.github.vishvakalhara.handymanbackend.domains.dtos.reviews.SimpleReviewDTO;
import io.github.vishvakalhara.handymanbackend.domains.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {

    List<SimpleReviewDTO> entityToSimpleReviewDTO(List<Review> reviews);
}
