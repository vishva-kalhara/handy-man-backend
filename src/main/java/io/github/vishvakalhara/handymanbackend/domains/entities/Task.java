package io.github.vishvakalhara.handymanbackend.domains.entities;

import io.github.vishvakalhara.handymanbackend.domains.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private Double maxPrice;

    private Boolean isEmergency;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

//    private Boolean isCompleted;

    private Boolean isDeleted;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "associatedTask")
    private List<Bid> bids = new ArrayList<>();

    @OneToMany(mappedBy = "task")
    private List<Review> reviews = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.taskStatus = TaskStatus.PENDING;
        this.isDeleted = false;
        this.createdAt = LocalDateTime.now();
    }
}