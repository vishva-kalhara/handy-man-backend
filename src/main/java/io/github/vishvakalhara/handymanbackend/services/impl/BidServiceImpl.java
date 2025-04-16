package io.github.vishvakalhara.handymanbackend.services.impl;

import io.github.vishvakalhara.handymanbackend.domains.BidStatus;
import io.github.vishvakalhara.handymanbackend.domains.TaskStatus;
import io.github.vishvakalhara.handymanbackend.domains.dtos.bids.CreateBidRequest;
import io.github.vishvakalhara.handymanbackend.domains.entities.Bid;
import io.github.vishvakalhara.handymanbackend.domains.entities.Notification;
import io.github.vishvakalhara.handymanbackend.domains.entities.Task;
import io.github.vishvakalhara.handymanbackend.domains.entities.User;
import io.github.vishvakalhara.handymanbackend.error_handling.AppException;
import io.github.vishvakalhara.handymanbackend.repositories.BidRepo;
import io.github.vishvakalhara.handymanbackend.repositories.TaskRepo;
import io.github.vishvakalhara.handymanbackend.repositories.UserRepo;
import io.github.vishvakalhara.handymanbackend.services.BidService;
import io.github.vishvakalhara.handymanbackend.util.NotificationText;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BidServiceImpl implements BidService {

    private final TaskRepo taskRepo;

    private final UserRepo userRepo;

    private final BidRepo bidRepo;

    private final NotificationServiceImpl notificationServiceImpl;

    @Transactional
    @Override
    public Bid createBid(CreateBidRequest data) {

        Task associatedTask = taskRepo.findById(data.getTaskId()).orElseThrow(
                () -> new AppException("Task Not Found!", HttpStatus.NOT_FOUND)
        );

        if (associatedTask.getIsDeleted()) {
            throw new AppException("Task Not Found!", HttpStatus.NOT_FOUND);
        }

        if (associatedTask.getTaskStatus() != TaskStatus.PENDING) {
            throw new AppException("Task does not accept bids anymore!", HttpStatus.FORBIDDEN);
        }

        User taskCreator = associatedTask.getCreator();
        if (taskCreator.getId().equals(data.getBidderId())) {
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

        // Notification for Task Owner
        notificationServiceImpl.AddNotification(
                "New Bid for Your Task!",
                NotificationText.getMessageToTaskOwnerAtBidSubmission(
                        bidder.getDisplayName(),
                        bid.getPrice(),
                        associatedTask.getTitle()
                ),
                "/tasks/" + associatedTask.getId(),
                taskCreator
        );

        // Notification for Handyman
        notificationServiceImpl.AddNotification(
                "Bid Submitted Successfully!",
                NotificationText.getMessageToHandyManAtBidSubmission(
                        bid.getPrice(),
                        associatedTask.getTitle()
                ),
                "/tasks/" + associatedTask.getId(),
                bidder
        );

        return bidRepo.save(bid);
    }

    @Transactional
    @Override
    public Bid updateBidStatus(UUID bidId, BidStatus bidStatus, UUID taskOwnerId) {

        Bid bid = bidRepo.findById(bidId).orElseThrow(
                () -> new AppException("Bid Not Found!", HttpStatus.NOT_FOUND)
        );

        if (bid.getBidStatus() != BidStatus.PENDING || bidStatus == BidStatus.PENDING) {
            throw new AppException("Invalid bid status!");
        }

        if (!bid.getAssociatedTask().getCreator().getId().equals(taskOwnerId)) {
            throw new AppException("Only task owner can perform this action!", HttpStatus.FORBIDDEN);
        }

        // Update the selected bid
        bid.setBidStatus(bidStatus);

        if (bidStatus == BidStatus.REJECTED) {

            notificationServiceImpl.AddNotification(
                    "Bid Not Selected",
                    NotificationText.getMessageToHandyManAtBidRejection(
                            bid.getPrice(),
                            bid.getAssociatedTask().getTitle()
                    ),
                    "/tasks/" + bid.getAssociatedTask().getId(),
                    bid.getBidder()
            );

            return bidRepo.save(bid);
        }

        // Get all the pending bids associating to the task
        List<Bid> bids = bidRepo.findBidsByBidStatusAndAssociatedTask_Id(
                BidStatus.PENDING,
                bid.getAssociatedTask().getId()
        );

        // Rejecting the bids
        List<Bid> bidsToBeRejected = bids.stream().peek(
                (b) -> b.setBidStatus(BidStatus.REJECTED)
        ).toList();

        List<Bid> bidsToBeUpdated = new ArrayList<>(bidsToBeRejected);
        bidsToBeUpdated.add(bid);

        bidRepo.saveAll(bidsToBeUpdated);

        List<Notification> notifications = getRejectedNotifications(
                bidsToBeRejected,
                bid.getAssociatedTask().getId()
        );
        notificationServiceImpl.AddNotification(notifications);

        notificationServiceImpl.AddNotification(
                "Your Bid Was Accepted!",
                NotificationText.getMessageToHandyManAtBidAccepted(
                        bid.getPrice(),
                        bid.getAssociatedTask().getTitle()
                ),
                "/tasks/" + bid.getAssociatedTask().getId(),
                true,
                bid.getBidder()
        );

        // Updating the task status
        Task task = bid.getAssociatedTask();
        task.setTaskStatus(TaskStatus.WAITING_TO_COMPLETE);
        taskRepo.save(task);

        return bid;
    }

    private List<Notification> getRejectedNotifications(List<Bid> bids, UUID taskId) {

        List<Notification> notifications = new ArrayList<>();
        for (Bid b : bids) {

            notifications.add(Notification.builder()
                    .title("Bid Not Selected")
                    .message(
                            NotificationText.getMessageToHandyManAtBidRejection(
                                    b.getPrice(),
                                    b.getAssociatedTask().getTitle()
                            ))
                    .href("/tasks/" + taskId)
                    .hasNoted(false)
                    .associatedUser(b.getBidder())
                    .build()
            );
        }

        return notifications;
    }
}
