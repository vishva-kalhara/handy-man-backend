package io.github.vishvakalhara.handymanbackend.mappers;

import io.github.vishvakalhara.handymanbackend.domains.dtos.messages.MessageDTO;
import io.github.vishvakalhara.handymanbackend.domains.entities.Message;
import io.github.vishvakalhara.handymanbackend.domains.entities.User;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageMapper {

    @Mapping(target = "isSentByMe", source = "sender", qualifiedByName = "mapIsSentByMe")
    MessageDTO entityToDTO(Message message, @Context UUID myId);

    @Named("mapIsSentByMe")
    default boolean mapIsSentByMe(User sender, @Context UUID myId){

        return sender.getId().equals(myId);
    }
}
