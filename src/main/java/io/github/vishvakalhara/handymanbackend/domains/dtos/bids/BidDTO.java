package io.github.vishvakalhara.handymanbackend.domains.dtos.bids;

import io.github.vishvakalhara.handymanbackend.domains.dtos.user.SimpleUserDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class BidDTO {

    private UUID id;
    private Double price;
    private String bidStatus;
    private LocalDateTime createdAt;
    private SimpleUserDTO bidder;
}
