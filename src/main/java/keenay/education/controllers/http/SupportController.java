package keenay.education.controllers.http;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import keenay.education.dto.support.TicketAnswerBodyDTO;
import keenay.education.dto.support.TicketBodyDTO;
import keenay.education.dto.support.TicketDTO;
import keenay.education.dto.support.TicketWithAnswerDTO;
import keenay.education.security.CustomUserDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Support Endpoints")
@RequestMapping("/api/v1/support")
public interface SupportController {
    ResponseEntity<TicketWithAnswerDTO> createTicket(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @Valid @RequestBody TicketBodyDTO ticketBodyDTO
    );
    ResponseEntity<TicketWithAnswerDTO> getTicketInfo(
            @AuthenticationPrincipal CustomUserDetail customUserDetail,
            @PathParam("id")Long id
    );
    ResponseEntity<TicketWithAnswerDTO> answerForTicket(
            @AuthenticationPrincipal CustomUserDetail customUserDetail,
            @PathParam("id") Long id,
            @Valid @RequestBody TicketAnswerBodyDTO ticketAnswerBodyDTO
    );
    ResponseEntity<List<TicketDTO>> getAvailableTicket();

}
