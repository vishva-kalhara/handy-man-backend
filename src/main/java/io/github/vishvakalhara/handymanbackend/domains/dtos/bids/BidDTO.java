package io.github.vishvakalhara.handymanbackend.domains.dtos.bids;

import io.github.vishvakalhara.handymanbackend.domains.BidStatus;
import io.github.vishvakalhara.handymanbackend.domains.dtos.user.OnlyUserId;
import io.github.vishvakalhara.handymanbackend.domains.entities.Task;
import io.github.vishvakalhara.handymanbackend.domains.entities.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class BidDTO {

    private UUID id;
    private Double price;
    private String bidStatus;
    private LocalDateTime createdAt;
    private OnlyUserId bidder;
}
