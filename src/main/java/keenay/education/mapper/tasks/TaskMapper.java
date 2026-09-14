package keenay.education.mapper.tasks;

import keenay.education.dto.tasks.TaskDTO;
import keenay.education.entity.Tasks;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mappings({
            @Mapping(source = "task.id", target="id"),
            @Mapping(source = "task.title", target = "title"),
            @Mapping(source = "task.description", target = "description"),
            @Mapping(source = "task.photo", target = "animal"),
            @Mapping(source = "task.advertisement.id", target = "advertisementId"),
            @Mapping(source = "task.status", target = "status")
    })
    TaskDTO getDTO(Tasks task);
}
