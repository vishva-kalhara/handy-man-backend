package io.github.vishvakalhara.handymanbackend.mappers;

import io.github.vishvakalhara.handymanbackend.domains.dtos.bids.BidDTO;
import io.github.vishvakalhara.handymanbackend.domains.dtos.user.SimpleUserDTO;
import io.github.vishvakalhara.handymanbackend.domains.entities.Bid;
import io.github.vishvakalhara.handymanbackend.domains.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BidMapper {

    @Mapping(target = "bidder", source = "bidder", qualifiedByName = "mapBidderToDTO")
    BidDTO entityToDTO(Bid bid);

    @Named("mapBidderToDTO")
    default SimpleUserDTO mapBidderToDTO(User bidder){
        return SimpleUserDTO.builder()
                .id(bidder.getId())
                .displayName(bidder.getDisplayName())
                .profileImage(bidder.getProfileImage())
                .avgRating(
                        bidder.getTotalReviewsCount() == 0 ?
                                null
                                :
                                bidder.getTotalReviewsValue() / bidder.getTotalReviewsCount()
                )
                .build();
    }
}
