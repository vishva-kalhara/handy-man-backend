package io.github.vishvakalhara.handymanbackend.mappers;

import io.github.vishvakalhara.handymanbackend.domains.BidStatus;
import io.github.vishvakalhara.handymanbackend.domains.TaskStatus;
import io.github.vishvakalhara.handymanbackend.domains.dtos.categories.CategoryDTO;
import io.github.vishvakalhara.handymanbackend.domains.dtos.reviews.ReviewDTO;
import io.github.vishvakalhara.handymanbackend.domains.dtos.tasks.SimpleTaskDTO;
import io.github.vishvakalhara.handymanbackend.domains.dtos.tasks.TaskDTO;
import io.github.vishvakalhara.handymanbackend.domains.dtos.user.SimpleUserDTO;
import io.github.vishvakalhara.handymanbackend.domains.entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    @Mapping(target = "creator", source = "creator", qualifiedByName = "removeCreatorDetails")
    @Mapping(target = "category", source = "category", qualifiedByName = "mapCategory")
    @Mapping(target = "taskStatus", source = "taskStatus", qualifiedByName = "mapTaskStatus")
    @Mapping(target = "chosenBidder", source = "bids", qualifiedByName = "mapChosenBidder")
    @Mapping(target = "reviews", source = "reviews", qualifiedByName = "mapReviews")
    TaskDTO entityToDTO(Task task);

    @Mapping(target = "category", source = "category", qualifiedByName = "mapCategory")
    @Mapping(target = "taskStatus", source = "taskStatus", qualifiedByName = "mapTaskStatus")
    List<SimpleTaskDTO> entityToSimpleTaskDTO(List<Task> tasks);

    @Mapping(target = "category", source = "category", qualifiedByName = "mapCategory")
    @Mapping(target = "taskStatus", source = "taskStatus", qualifiedByName = "mapTaskStatus")
    SimpleTaskDTO entityToSimpleTaskDTO(Task task);

    @Named("mapReviews")
    default List<ReviewDTO> mapReviews(List<Review> reviews) {

        if (reviews == null || reviews.isEmpty()) {
            return null;
        }

        List<ReviewDTO> reviewDTOs = new ArrayList<>();
        for (Review r : reviews) {
            System.out.println(r.getReviewText());
            reviewDTOs.add(ReviewDTO.builder()
                    .reviewedById(r.getReviewedBy().getId())
                    .reviewText(r.getReviewText())
                    .ratedValue(r.getRatedValue())
                    .id(r.getId())
                    .build());
        }

        return reviewDTOs;
    }

    @Named("mapChosenBidder")
    default SimpleUserDTO mapChosenBidder(List<Bid> bids) {

        if (bids == null || bids.isEmpty())
            return null;

        for (Bid bid : bids) {
            if (bid.getBidStatus() == BidStatus.ACCEPTED) {
                return removeCreatorDetails(bid.getBidder());
            }
        }

        return null;
    }

    @Named("removeCreatorDetails")
    default SimpleUserDTO removeCreatorDetails(User creator) {

        return SimpleUserDTO.builder()
                .id(creator.getId())
                .displayName(creator.getDisplayName())
                .profileImage(creator.getProfileImage())
                .avgRating(
                        creator.getTotalReviewsCount() == 0 ?
                                null
                                :
                                creator.getTotalReviewsValue() / creator.getTotalReviewsCount()
                )
                .build();
    }

    @Named("mapCategory")
    default CategoryDTO mapCategory(Category category) {

        return new CategoryDTO(
                category.getId(),
                category.getCategoryName()
        );
    }

    @Named("mapTaskStatus")
    default String mapTaskStatus(TaskStatus taskStatus) {

        return taskStatus.name();
    }
}
