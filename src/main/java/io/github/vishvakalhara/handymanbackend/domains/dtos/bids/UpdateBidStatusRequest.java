package io.github.vishvakalhara.handymanbackend.domains.dtos.bids;

import io.github.vishvakalhara.handymanbackend.domains.BidStatus;
import lombok.Data;

@Data
public class UpdateBidStatusRequest {

    private BidStatus bidStatus;
}
