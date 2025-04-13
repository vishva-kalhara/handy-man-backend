package io.github.vishvakalhara.handymanbackend.mappers;

import io.github.vishvakalhara.handymanbackend.domains.TaskStatus;
import io.github.vishvakalhara.handymanbackend.domains.dtos.bids.BidDTO;
import io.github.vishvakalhara.handymanbackend.domains.dtos.categories.CategoryDTO;
import io.github.vishvakalhara.handymanbackend.domains.dtos.tasks.SimpleTaskDTO;
import io.github.vishvakalhara.handymanbackend.domains.dtos.tasks.TaskDTO;
import io.github.vishvakalhara.handymanbackend.domains.dtos.user.OnlyUserId;
import io.github.vishvakalhara.handymanbackend.domains.dtos.user.SimpleUserDTO;
import io.github.vishvakalhara.handymanbackend.domains.entities.Bid;
import io.github.vishvakalhara.handymanbackend.domains.entities.Category;
import io.github.vishvakalhara.handymanbackend.domains.entities.Task;
import io.github.vishvakalhara.handymanbackend.domains.entities.User;
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
    @Mapping(target = "bids", source = "bids", qualifiedByName = "mapTaskBids")
    TaskDTO entityToDTO(Task task);

    @Mapping(target = "category", source = "category", qualifiedByName = "mapCategory")
    @Mapping(target = "taskStatus", source = "taskStatus", qualifiedByName = "mapTaskStatus")
    List<SimpleTaskDTO> entityToSimpleTaskDTO(List<Task> tasks);

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

    @Named("mapTaskBids")
    default List<BidDTO> mapTaskBids(List<Bid> bids) {

        if(bids == null || bids.isEmpty())
            return null;

        List<BidDTO> bidDTOs = new ArrayList<>();

        for (Bid bid : bids) {
            bidDTOs.add(BidDTO.builder()
                    .id(bid.getId())
                    .price(bid.getPrice())
                    .bidStatus(bid.getBidStatus().name())
                    .createdAt(bid.getCreatedAt())
                    .bidder(new OnlyUserId(bid.getBidder().getId()))
                    .build());
        }

        return bidDTOs;
    }
}
