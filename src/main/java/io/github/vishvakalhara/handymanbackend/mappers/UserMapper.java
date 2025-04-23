package io.github.vishvakalhara.handymanbackend.mappers;

import io.github.vishvakalhara.handymanbackend.domains.dtos.auth.RegisterRequest;
import io.github.vishvakalhara.handymanbackend.domains.dtos.user.SimpleUserDTO;
import io.github.vishvakalhara.handymanbackend.domains.dtos.user.UserDTO;
import io.github.vishvakalhara.handymanbackend.domains.entities.Review;
import io.github.vishvakalhara.handymanbackend.domains.entities.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User registerRequestToEntity(RegisterRequest request);

    @Mapping(target = "avgRating", source = "reviewsIGot", qualifiedByName = "calculateAvgRating")
    UserDTO entityToGetMe(User user);

    @IterableMapping(qualifiedByName = "entityToSimpleUserDTO")
    List<SimpleUserDTO> entityToSimpleUserDTO(List<User> users);

    @Named("entityToSimpleUserDTO")
    @Mapping(target = "avgRating", source = "reviewsIGot", qualifiedByName = "calculateAvgRating")
    SimpleUserDTO entityToSimpleUserDTO(User user);

    @Named("calculateAvgRating")
    default Double calculateAvgRating(List<Review> reviewsIGot){

        if(reviewsIGot == null || reviewsIGot.isEmpty()){
            return null;
        }

        double ratingTotal = 0.0;
        int reviewCount = 0;

        for(Review review : reviewsIGot){
            ratingTotal += review.getRatedValue();
            reviewCount++;
        }

        double average = ratingTotal / reviewCount;
        return Math.round(average * 10.0) / 10.0;
    }
}
