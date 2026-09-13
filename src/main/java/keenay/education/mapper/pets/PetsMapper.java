package keenay.education.mapper.pets;

import keenay.education.dto.pets.PetsDTO;
import keenay.education.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PetsMapper {
    @Mappings({
            @Mapping(source = "pet.id", target="id"),
            @Mapping(source = "pet.name", target = "namePet"),
            @Mapping(source = "pet.animal.name", target = "nameAnimal"),
            @Mapping(source = "pet.petsProfile.breed", target = "breed"),
            @Mapping(source = "pet.petsProfile.features", target = "features"),
            @Mapping(source = "pet.petsProfile.vaccinations", target = "vaccinations")
    })
    PetsDTO getPets(Pets pet);
}
