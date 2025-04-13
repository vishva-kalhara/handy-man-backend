package io.github.vishvakalhara.handymanbackend.controllers;

import io.github.vishvakalhara.handymanbackend.domains.dtos.bids.BidDTO;
import io.github.vishvakalhara.handymanbackend.domains.dtos.bids.CreateBidRequest;
import io.github.vishvakalhara.handymanbackend.domains.dtos.bids.UpdateBidRequest;
import io.github.vishvakalhara.handymanbackend.domains.entities.Bid;
import io.github.vishvakalhara.handymanbackend.mappers.BidMapper;
import io.github.vishvakalhara.handymanbackend.services.BidService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/bids")
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;

    private final BidMapper bidMapper;

    @PostMapping
    public ResponseEntity<BidDTO> createBid(
            @Valid @RequestBody CreateBidRequest requestBody,
            @RequestAttribute UUID userId
    ){

        requestBody.setBidderId(userId);
        Bid createdBid = bidService.createBid(requestBody);

        return new ResponseEntity<>(bidMapper.entityToDTO(createdBid), HttpStatus.CREATED);
    }

    @PatchMapping("/{bidId}")
    public ResponseEntity<BidDTO> updateBidStatus(
            @PathVariable UUID bidId,
            @Valid @RequestBody UpdateBidRequest requestBody,
            @RequestAttribute UUID userId
    ){

        Bid bid = bidService.updateBidStatus(bidId, requestBody.getBidStatus(), userId);
        return ResponseEntity.ok(bidMapper.entityToDTO(bid));
    }
}