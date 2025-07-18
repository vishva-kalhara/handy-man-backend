package io.github.vishvakalhara.handymanbackend.services;

import io.github.vishvakalhara.handymanbackend.domains.dtos.reviews.CreateReviewRequest;
import io.github.vishvakalhara.handymanbackend.domains.entities.Review;

import java.util.List;
import java.util.UUID;

public interface ReviewService {

    List<Review> getReviewsUserGot(UUID userId, boolean isDesc);

    Review createReview(CreateReviewRequest reqBody, UUID reviewedById);
}
