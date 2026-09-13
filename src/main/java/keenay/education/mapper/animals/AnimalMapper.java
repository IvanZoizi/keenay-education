package keenay.education.mapper.animals;

import keenay.education.dto.animal.AnimalDTO;
import keenay.education.entity.Animals;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AnimalMapper {
    @Mappings({
            @Mapping(source = "name", target = "name")
    })
    AnimalDTO getAnimal(Animals animal);
}
