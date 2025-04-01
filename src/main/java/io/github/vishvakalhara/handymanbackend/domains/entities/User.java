package io.github.vishvakalhara.handymanbackend.domains.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String tempCode;

    private String profileImage;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Double totalReviewsValue;

    @Column(nullable = false)
    private Integer totalReviewsCount;

    @OneToMany(mappedBy = "creator")
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "bidder")
    private List<Bid> bids = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "recipient")
    private List<Message> receivedMessages;

    @OneToMany(mappedBy = "reviewedBy")
    private List<Review> reviewsIDid;

    @OneToMany(mappedBy = "reviewedTo")
    private List<Review> reviewsIGot;

    @PrePersist
    protected void onCreate(){
        this.isActive = true;
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = this.updatedAt = now;
        this.totalReviewsValue = 0.0;
        this.totalReviewsCount = 0;
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}