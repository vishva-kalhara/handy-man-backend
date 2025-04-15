package io.github.vishvakalhara.handymanbackend.domains.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.github.vishvakalhara.handymanbackend.domains.ReviewGotAsRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reviews")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Double ratedValue;

    @Column(columnDefinition = "TEXT")
    private String reviewText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by", nullable = false)
    @JsonBackReference
    private User reviewedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_to", nullable = false)
    @JsonBackReference
    private User reviewedTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    @JsonBackReference
    private Task task;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReviewGotAsRole reviewGotAsRole;

    private LocalDateTime reviewedAt;

    @PrePersist
    protected void onCreate(){
        this.reviewedAt = LocalDateTime.now();
    }
}
