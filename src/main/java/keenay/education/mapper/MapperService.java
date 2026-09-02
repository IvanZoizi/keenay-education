package keenay.education.mapper;

import keenay.education.dto.animal.AnimalDTO;
import keenay.education.dto.support.TicketDTO;
import keenay.education.dto.support.TicketWithAnswerDTO;
import keenay.education.entity.Animals;
import keenay.education.entity.Ticket;
import keenay.education.entity.TicketReplies;
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

    default String getAnswer(Ticket ticket) {
        return ticket.getTicketReplies() != null
                ? ticket.getTicketReplies().getAnswer()
                : "";
    }

    @Mappings({
            @Mapping(source = "name", target = "name")
    })
    AnimalDTO getAnimal(Animals animal);
}
