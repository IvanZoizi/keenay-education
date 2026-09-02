package keenay.education.service.impl;

import keenay.education.dto.support.TicketAnswerBodyDTO;
import keenay.education.dto.support.TicketBodyDTO;
import keenay.education.dto.support.TicketDTO;
import keenay.education.dto.support.TicketWithAnswerDTO;
import keenay.education.entity.Ticket;
import keenay.education.entity.TicketReplies;
import keenay.education.exception.errors.AccessDeniedException;
import keenay.education.exception.errors.EntityNotFoundException;
import keenay.education.mapper.MapperService;
import keenay.education.repository.TicketRepliesRepository;
import keenay.education.repository.TicketRepository;
import keenay.education.security.CustomUserDetail;
import keenay.education.service.SupportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SupportServiceImpl implements SupportService {

    private final MapperService mapperService;
    private final TicketRepository ticketRepository;
    private final TicketRepliesRepository ticketRepliesRepository;


    @Override
    public TicketWithAnswerDTO createTicket(CustomUserDetail customUserDetail, TicketBodyDTO ticketBodyDTO) {
        Ticket ticket = new Ticket();
        ticket.setQuestion(ticketBodyDTO.getQuestion());
        ticket.setSender(customUserDetail.getUser());
        ticket.setAdmin(null);
        return mapperService.getTicketWithAnswerDTO(ticketRepository.save(ticket));
    }

    @Override
    public TicketWithAnswerDTO getTicketInfo(CustomUserDetail customUserDetail, Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The ticket was not found."));
        if (!Objects.equals(ticket.getSender().getId(), customUserDetail.getUser().getId())) {
            throw new AccessDeniedException("You cannot get information about this ticket.");
        }
        return mapperService.getTicketWithAnswerDTO(ticket);
    }

    @Override
    public TicketWithAnswerDTO answerForTicket(CustomUserDetail customUserDetail, Long id, TicketAnswerBodyDTO ticketAnswerBodyDTO) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The ticket was not found."));
        TicketReplies ticketReplies = new TicketReplies();
        ticketReplies.setAnswer(ticketAnswerBodyDTO.getAnswer());
        ticketReplies.setTicket(ticket);
        ticketRepliesRepository.save(ticketReplies);
        ticket.setAdmin(customUserDetail.getUser());
        return mapperService.getTicketWithAnswerDTO(ticketRepository.save(ticket));
    }

    @Override
    public List<TicketDTO> getAvailableTicket() {
        return ticketRepository.findAll().stream()
                .map(mapperService::getTicketDTO)
                .toList();
    }
}
