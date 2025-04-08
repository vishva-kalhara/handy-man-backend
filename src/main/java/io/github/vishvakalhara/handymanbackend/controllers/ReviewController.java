package io.github.vishvakalhara.handymanbackend.controllers;

import io.github.vishvakalhara.handymanbackend.domains.dtos.reviews.ReviewDTO;
import io.github.vishvakalhara.handymanbackend.domains.dtos.reviews.UpdateReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    /*
    1. The system creates the review
    2. User has to provide rating and review text and modify it
     */
    @PostMapping
    public ResponseEntity<ReviewDTO> updateReview(
            @PathVariable UUID reviewId, @RequestBody UpdateReviewRequest requestBody){

        return ResponseEntity.ok(new ReviewDTO());
    }

    @GetMapping("/me")
    public ResponseEntity<List<ReviewDTO>> getMyReviews(){

        // Get User UUID from request scope
        // reviewService.getMyReviewsIGot();
        return ResponseEntity.ok(new ArrayList<>());
    }
}