package io.github.vishvakalhara.handymanbackend.repositories;

import io.github.vishvakalhara.handymanbackend.domains.entities.Message;
import io.github.vishvakalhara.handymanbackend.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepo extends JpaRepository<Message, UUID> {

    @Query("""
        SELECT DISTINCT u
        FROM User u
        WHERE u.id IN (
            SELECT m.sender.id FROM Message m WHERE m.recipient.id = :userId
            UNION
            SELECT m.recipient.id FROM Message m WHERE m.sender.id = :userId
        )
    """)
    List<User> findDistinctUsersCommunicatedWith(UUID myId);
}
