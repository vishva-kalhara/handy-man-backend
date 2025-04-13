package io.github.vishvakalhara.handymanbackend.repositories;

import io.github.vishvakalhara.handymanbackend.domains.TaskStatus;
import io.github.vishvakalhara.handymanbackend.domains.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepo extends JpaRepository<Task, UUID> {

    @Query("SELECT t from Task t " +
            "WHERE (:isEmergency IS NULL OR t.isEmergency = :isEmergency)" +
            "AND (:isDeleted IS NULL OR t.isDeleted = :isDeleted)" +
            "AND (:creatorId IS NULL OR t.creator.id = :creatorId)" +
            "AND (:categoryName IS NULL OR t.category.categoryName = :categoryName)" +
            "AND (:taskStatus IS NULL OR t.taskStatus = :taskStatus)" +
            "AND (:minPrice IS NULL OR t.maxPrice >= :minPrice)" +
            "AND (:maxPrice IS NULL OR t.maxPrice <= :maxPrice)"
    )
    List<Task> filterTasks(
            @Param("isEmergency") Boolean isEmergency,
            @Param("creatorId") UUID creatorId,
            @Param("categoryName") String categoryName,
            @Param("taskStatus") TaskStatus taskStatus,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("isDeleted") Boolean isDeleted,
            Pageable pageable
    );
}
