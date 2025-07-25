package io.github.vishvakalhara.handymanbackend.services.impl;

import io.github.vishvakalhara.handymanbackend.domains.ReviewGotAsRole;
import io.github.vishvakalhara.handymanbackend.domains.dtos.reviews.CreateReviewRequest;
import io.github.vishvakalhara.handymanbackend.domains.entities.Review;
import io.github.vishvakalhara.handymanbackend.domains.entities.Task;
import io.github.vishvakalhara.handymanbackend.domains.entities.User;
import io.github.vishvakalhara.handymanbackend.error_handling.AppException;
import io.github.vishvakalhara.handymanbackend.repositories.ReviewRepo;
import io.github.vishvakalhara.handymanbackend.repositories.TaskRepo;
import io.github.vishvakalhara.handymanbackend.repositories.UserRepo;
import io.github.vishvakalhara.handymanbackend.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final UserRepo userRepo;

    private final TaskRepo taskRepo;

    private final ReviewRepo reviewRepo;

    @Override
    public List<Review> getReviewsUserGot(UUID userId, boolean isDesc) {

        if(!userRepo.existsUserById(userId)){
            throw new AppException("User not found!", HttpStatus.NOT_FOUND);
        }

        if(isDesc)
            return reviewRepo.findTop10ByReviewedTo_IdOrderByRatedValueDesc(userId);

        return reviewRepo.findTop10ByReviewedTo_IdOrderByRatedValueAsc(userId);
    }

    @Override
    public Review createReview(CreateReviewRequest reqBody, UUID reviewedById) {

        if(reqBody.getReviewedToId().equals(reviewedById)) {
            throw new AppException("You cannot review yourself!", HttpStatus.FORBIDDEN);
        }

        // Check whether already there is already a review
        if(reviewRepo.existsReviewByTask_IdAndReviewedBy_IdAndReviewedTo_Id(
                reqBody.getTaskId(),
                reviewedById,
                reqBody.getReviewedToId()
        )) {
            throw new AppException("There is already a review!", HttpStatus.FORBIDDEN);
        }

        User reviewedToUser = userRepo.findById(reqBody.getReviewedToId()).orElseThrow(
                () -> new AppException("Reviewed to user is not found! Try refreshing the page", HttpStatus.NOT_FOUND)
        );

        Task task = taskRepo.findById(reqBody.getTaskId()).orElseThrow(
                () -> new AppException("Task is not found! Try refreshing the page.", HttpStatus.NOT_FOUND)
        );

        User reviewedByUser = userRepo.findById(reviewedById).orElseThrow(
                () -> new AppException("Reviewed by user is not found! Try refreshing the page", HttpStatus.NOT_FOUND)
        );

        Review review = Review.builder()
                .ratedValue(reqBody.getRatedValue())
                .reviewText(reqBody.getReviewText())
                .reviewedBy(reviewedByUser)
                .reviewedTo(reviewedToUser)
                .task(task)
                .reviewGotAsRole(
                        task.getCreator().getId().equals(reviewedById) ?
                                ReviewGotAsRole.HANDY_MAN
                                :
                                ReviewGotAsRole.TASK_OWNER
                )
                .build();

        return reviewRepo.save(review);
    }
}
