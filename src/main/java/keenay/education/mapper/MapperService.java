package keenay.education.mapper;

import keenay.education.dto.animal.AnimalDTO;
import keenay.education.dto.pets.PetsDTO;
import keenay.education.dto.skills.SkillsDTO;
import keenay.education.dto.support.TicketDTO;
import keenay.education.dto.support.TicketWithAnswerDTO;
import keenay.education.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
public interface MapperService {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "question", target = "question"),
            @Mapping(target = "answer", expression = "java(getAnswer(ticket))")
    })
    TicketWithAnswerDTO getTicketWithAnswerDTO(Ticket ticket);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "question", target = "question"),
    })
    TicketDTO getTicketDTO(Ticket ticket);

    @Mappings({
            @Mapping(source = "name", target = "name")
    })
    AnimalDTO getAnimal(Animals animal);

    @Mappings({
            @Mapping(source = "pet.id", target="id"),
            @Mapping(source = "pet.name", target = "namePet"),
            @Mapping(source = "pet.animal.name", target = "nameAnimal"),
            @Mapping(source = "pet.petsProfile.breed", target = "breed"),
            @Mapping(source = "pet.petsProfile.features", target = "features"),
            @Mapping(source = "pet.petsProfile.vaccinations", target = "vaccinations")
    })
    PetsDTO getPets(Pets pet);

    @Mappings({
            @Mapping(source = "skill.id", target="id"),
            @Mapping(source = "skill.title", target = "title"),
            @Mapping(source = "skill.description", target = "description"),
            @Mapping(source = "skill.animal.name", target = "animal")
    })
    SkillsDTO getSkill(Skills skill);

    default String getAnswer(Ticket ticket) {
        return ticket.getTicketReplies() != null
                ? ticket.getTicketReplies().getAnswer()
                : "";
    }
}
