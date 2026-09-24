package keenay.education.controller;

import keenay.education.controllers.http.impl.SupportControllerImpl;
import keenay.education.dto.support.TicketAnswerBodyDTO;
import keenay.education.dto.support.TicketBodyDTO;
import keenay.education.dto.support.TicketDTO;
import keenay.education.dto.support.TicketWithAnswerDTO;
import keenay.education.security.CustomUserDetail;
import keenay.education.service.SupportService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SupportControllerImplTest {

    private static final Long TICKET_ID = 10L;

    @Mock
    private SupportService supportService;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private CustomUserDetail userDetail;

    @InjectMocks
    private SupportControllerImpl supportController;

    private TicketBodyDTO ticketBody() {
        TicketBodyDTO body = new TicketBodyDTO();
        body.setQuestion("Question");
        return body;
    }

    private TicketAnswerBodyDTO answerBody() {
        TicketAnswerBodyDTO body = new TicketAnswerBodyDTO();
        body.setAnswer("Answer");
        return body;
    }

    private TicketWithAnswerDTO ticketWithAnswer(Long id) {
        TicketWithAnswerDTO dto = new TicketWithAnswerDTO();
        dto.setId(id);
        dto.setQuestion("Question");
        dto.setAnswer("Answer");
        return dto;
    }

    private TicketDTO ticketDto(Long id) {
        TicketDTO dto = new TicketDTO();
        dto.setId(id);
        dto.setQuestion("Question");
        return dto;
    }

    @Test
    @DisplayName("Тест createTicket")
    public void testCreateTicketSuccess() {
        TicketWithAnswerDTO expected = ticketWithAnswer(TICKET_ID);
        when(supportService.createTicket(eq(userDetail), any(TicketBodyDTO.class))).thenReturn(expected);

        ResponseEntity<TicketWithAnswerDTO> response = supportController.createTicket(userDetail, ticketBody());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(supportService).createTicket(eq(userDetail), any(TicketBodyDTO.class));
    }

    @Test
    @DisplayName("Тест getTicketInfo")
    public void testGetTicketInfoSuccess() {
        TicketWithAnswerDTO expected = ticketWithAnswer(TICKET_ID);
        when(supportService.getTicketInfo(userDetail, TICKET_ID)).thenReturn(expected);

        ResponseEntity<TicketWithAnswerDTO> response = supportController.getTicketInfo(userDetail, TICKET_ID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(supportService).getTicketInfo(userDetail, TICKET_ID);
    }

    @Test
    @DisplayName("Тест answerForTicket")
    public void testAnswerForTicketSuccess() {
        TicketWithAnswerDTO expected = ticketWithAnswer(TICKET_ID);
        when(supportService.answerForTicket(eq(userDetail), eq(TICKET_ID), any(TicketAnswerBodyDTO.class)))
                .thenReturn(expected);

        ResponseEntity<TicketWithAnswerDTO> response =
                supportController.answerForTicket(userDetail, TICKET_ID, answerBody());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(supportService).answerForTicket(eq(userDetail), eq(TICKET_ID), any(TicketAnswerBodyDTO.class));
    }

    @Test
    @DisplayName("Тест getAvailableTicket")
    public void testGetAvailableTicketSuccess() {
        List<TicketDTO> expected = List.of(ticketDto(1L), ticketDto(2L));
        when(supportService.getAvailableTicket()).thenReturn(expected);

        ResponseEntity<List<TicketDTO>> response = supportController.getAvailableTicket();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getId());
        assertEquals(2L, response.getBody().get(1).getId());
        verify(supportService).getAvailableTicket();
    }

    @Test
    @DisplayName("Тест getAvailableTicket пустой список")
    public void testGetAvailableTicketEmpty() {
        when(supportService.getAvailableTicket()).thenReturn(List.of());

        ResponseEntity<List<TicketDTO>> response = supportController.getAvailableTicket();

        assertNotNull(response);
        assertTrue(response.getBody().isEmpty());
        verify(supportService).getAvailableTicket();
    }
}