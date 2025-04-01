package io.github.vishvakalhara.handymanbackend.controllers;

import io.github.vishvakalhara.handymanbackend.domains.dtos.bids.BidDTO;
import io.github.vishvakalhara.handymanbackend.domains.dtos.bids.CreateBidRequest;
import io.github.vishvakalhara.handymanbackend.domains.dtos.bids.UpdateBidStatusRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/bids")
@RequiredArgsConstructor
public class BidController {

    @PostMapping
    public ResponseEntity<BidDTO> createBid(@RequestBody CreateBidRequest requestBody){

        // Get Bidder Id from request scope
        return new ResponseEntity<>(new BidDTO(), HttpStatus.CREATED);
    }

    @GetMapping("/byTask/{taskId}")
    public ResponseEntity<List<BidDTO>> getBidsOfATask(@PathVariable UUID taskId){

        return ResponseEntity.ok(new ArrayList<>());
    }

    @PatchMapping("/{bidId}")
    public ResponseEntity<BidDTO> updateBidStatus(
            @PathVariable UUID bidId, @RequestBody UpdateBidStatusRequest requestBody){

        return ResponseEntity.ok(new BidDTO());
    }
}
