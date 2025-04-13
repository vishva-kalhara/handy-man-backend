package io.github.vishvakalhara.handymanbackend.services;

import io.github.vishvakalhara.handymanbackend.domains.dtos.bids.CreateBidRequest;
import io.github.vishvakalhara.handymanbackend.domains.entities.Bid;

public interface BidService {
    Bid createBid(CreateBidRequest data);
}
