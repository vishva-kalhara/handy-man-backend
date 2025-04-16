package io.github.vishvakalhara.handymanbackend.mappers;

import io.github.vishvakalhara.handymanbackend.domains.dtos.notifications.NotificationDTO;
import io.github.vishvakalhara.handymanbackend.domains.entities.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationMapper {

    List<NotificationDTO> entityToDTO(List<Notification> notifications);
}
