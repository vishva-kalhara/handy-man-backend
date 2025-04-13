package io.github.vishvakalhara.handymanbackend.services.impl;

import io.github.vishvakalhara.handymanbackend.domains.TaskStatus;
import io.github.vishvakalhara.handymanbackend.domains.dtos.bids.CreateBidRequest;
import io.github.vishvakalhara.handymanbackend.domains.entities.Bid;
import io.github.vishvakalhara.handymanbackend.domains.entities.Task;
import io.github.vishvakalhara.handymanbackend.domains.entities.User;
import io.github.vishvakalhara.handymanbackend.error_handling.AppException;
import io.github.vishvakalhara.handymanbackend.repositories.BidRepo;
import io.github.vishvakalhara.handymanbackend.repositories.TaskRepo;
import io.github.vishvakalhara.handymanbackend.repositories.UserRepo;
import io.github.vishvakalhara.handymanbackend.services.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BidServiceImpl implements BidService {

    private final TaskRepo taskRepo;

    private final UserRepo userRepo;

    private final BidRepo bidRepo;

    @Override
    public Bid createBid(CreateBidRequest data) {

        Task associatedTask = taskRepo.findById(data.getTaskId()).orElseThrow(
                () -> new AppException("Task Not Found!", HttpStatus.NOT_FOUND)
        );

        if(associatedTask.getIsDeleted()){
            throw new AppException("Task Not Found!", HttpStatus.NOT_FOUND);
        }

        if(associatedTask.getTaskStatus() != TaskStatus.PENDING){
            throw new AppException("Task does not accept bids anymore!", HttpStatus.FORBIDDEN);
        }

        if(associatedTask.getCreator().getId().equals(data.getBidderId())){
            throw new AppException("Task owner cannot bid for the same task!", HttpStatus.FORBIDDEN);
        }

        User bidder = userRepo.findById(data.getBidderId()).orElseThrow(
                () -> new AppException("Bidder Not Found!", HttpStatus.NOT_FOUND)
        );

        Bid bid = Bid.builder()
                .price(data.getPrice())
                .associatedTask(associatedTask)
                .bidder(bidder)
                .build();

        return bidRepo.save(bid);
    }
}
