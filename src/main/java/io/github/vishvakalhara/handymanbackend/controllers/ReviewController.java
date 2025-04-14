package io.github.vishvakalhara.handymanbackend.controllers;

import io.github.vishvakalhara.handymanbackend.domains.dtos.reviews.ReviewDTO;
import io.github.vishvakalhara.handymanbackend.domains.dtos.reviews.CreateReviewRequest;
import io.github.vishvakalhara.handymanbackend.domains.entities.Review;
import io.github.vishvakalhara.handymanbackend.services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> updateReview(
            @RequestAttribute UUID userId,
            @Valid @RequestBody CreateReviewRequest requestBody
    ){

        Review review = reviewService.createReview(requestBody, userId);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<List<ReviewDTO>> getMyReviews(){

        // Get User UUID from request scope
        // reviewService.getMyReviewsIGot();
        return ResponseEntity.ok(new ArrayList<>());
    }
}