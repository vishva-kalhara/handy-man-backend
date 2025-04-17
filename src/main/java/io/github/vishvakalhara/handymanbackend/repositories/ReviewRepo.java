package io.github.vishvakalhara.handymanbackend.repositories;

import io.github.vishvakalhara.handymanbackend.domains.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepo extends JpaRepository<Review, UUID> {

    boolean existsReviewByTask_IdAndReviewedBy_IdAndReviewedTo_Id(UUID taskId, UUID reviewedById, UUID reviewedToId);

    List<Review> findTop10ByReviewedTo_IdOrderByRatedValueDesc(UUID reviewedToId);

    List<Review> findTop10ByReviewedTo_IdOrderByRatedValueAsc(UUID reviewedToId);
}
