package keenay.education.service;

import keenay.education.dto.support.TicketAnswerBodyDTO;
import keenay.education.dto.support.TicketBodyDTO;
import keenay.education.dto.support.TicketDTO;
import keenay.education.dto.support.TicketWithAnswerDTO;
import keenay.education.security.CustomUserDetail;

import java.util.List;

public interface SupportService {
    TicketWithAnswerDTO createTicket(CustomUserDetail customUserDetail, TicketBodyDTO ticketBodyDTO);
    TicketWithAnswerDTO getTicketInfo(CustomUserDetail customUserDetail, Long id);
    TicketWithAnswerDTO answerForTicket(CustomUserDetail customUserDetail, Long id, TicketAnswerBodyDTO ticketAnswerBodyDTO);
    List<TicketDTO> getAvailableTicket();
}
