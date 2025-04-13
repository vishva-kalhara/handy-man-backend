package io.github.vishvakalhara.handymanbackend.domains.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.github.vishvakalhara.handymanbackend.domains.BidStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bids")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BidStatus bidStatus;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    @JsonBackReference
    private Task associatedTask;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bidder_id", nullable = false)
    @JsonBackReference
    private User bidder;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.bidStatus = BidStatus.PENDING;
    }
}