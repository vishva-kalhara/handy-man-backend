package io.github.vishvakalhara.handymanbackend.mappers;

import io.github.vishvakalhara.handymanbackend.domains.dtos.tasks.TaskDTO;
import io.github.vishvakalhara.handymanbackend.domains.entities.Task;
import io.github.vishvakalhara.handymanbackend.domains.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    @Mapping(target = "creator", source = "creator", qualifiedByName = "removeCreatorDetails")
    TaskDTO entityToDTO(Task task);

    @Named("removeCreatorDetails")
    default User removeCreatorDetails(User creator){

        User user = new User();
        user.setId(creator.getId());

        return user;
    }
}
