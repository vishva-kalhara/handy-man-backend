package io.github.vishvakalhara.handymanbackend.services;

import io.github.vishvakalhara.handymanbackend.domains.BidStatus;
import io.github.vishvakalhara.handymanbackend.domains.dtos.bids.CreateBidRequest;
import io.github.vishvakalhara.handymanbackend.domains.entities.Bid;

import java.util.UUID;

public interface BidService {
    Bid createBid(CreateBidRequest data);

    Bid updateBidStatus(UUID bidId, BidStatus bidStatus, UUID taskOwnerId);
}
