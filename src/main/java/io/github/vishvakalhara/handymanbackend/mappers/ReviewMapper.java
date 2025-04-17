package io.github.vishvakalhara.handymanbackend.mappers;

import io.github.vishvakalhara.handymanbackend.domains.ReviewGotAsRole;
import io.github.vishvakalhara.handymanbackend.domains.dtos.reviews.SimpleReviewDTO;
import io.github.vishvakalhara.handymanbackend.domains.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {

    @Mapping(target = "reviewGotAsRole", source = "reviewGotAsRole", qualifiedByName = "mapReviewGotAsRole")
    List<SimpleReviewDTO> entityToSimpleReviewDTO(List<Review> reviews);

    @Named("mapReviewGotAsRole")
    default String mapReviewGotAsRole(ReviewGotAsRole reviewGotAsRole){

        return reviewGotAsRole.name();
    }
}
