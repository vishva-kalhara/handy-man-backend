package io.github.vishvakalhara.handymanbackend.domains.dtos.bids;

import io.github.vishvakalhara.handymanbackend.domains.BidStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateBidRequest {

    @NotNull(message = "Bid Status is required")
    private BidStatus bidStatus;
}
