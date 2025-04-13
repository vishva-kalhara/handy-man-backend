package io.github.vishvakalhara.handymanbackend.repositories;

import io.github.vishvakalhara.handymanbackend.domains.BidStatus;
import io.github.vishvakalhara.handymanbackend.domains.entities.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BidRepo extends JpaRepository<Bid, UUID> {

    List<Bid> findBidsByBidStatusAndAssociatedTask_Id(BidStatus bidStatus, UUID associatedTaskId);
}
