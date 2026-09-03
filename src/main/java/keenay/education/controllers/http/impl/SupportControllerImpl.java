package keenay.education.controllers.http.impl;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import keenay.education.controllers.http.SupportController;
import keenay.education.dto.support.TicketAnswerBodyDTO;
import keenay.education.dto.support.TicketBodyDTO;
import keenay.education.dto.support.TicketDTO;
import keenay.education.dto.support.TicketWithAnswerDTO;
import keenay.education.security.CustomUserDetail;
import keenay.education.service.SupportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SupportControllerImpl implements SupportController {

    private final SupportService supportService;

    @Override
    @PostMapping
    public ResponseEntity<TicketWithAnswerDTO> createTicket(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @Valid @RequestBody TicketBodyDTO ticketBodyDTO) {
        return ResponseEntity.ok(supportService.createTicket(userDetail, ticketBodyDTO));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<TicketWithAnswerDTO> getTicketInfo(
            @AuthenticationPrincipal CustomUserDetail customUserDetail,
            @PathVariable("id")Long id
    ) {
        return ResponseEntity.ok(supportService.getTicketInfo(customUserDetail, id));
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_admin')")
    @PostMapping("/answer/{id}")
    public ResponseEntity<TicketWithAnswerDTO> answerForTicket(
            @AuthenticationPrincipal CustomUserDetail customUserDetail,
            @PathVariable("id") Long id,
            @Valid @RequestBody TicketAnswerBodyDTO ticketAnswerBodyDTO
    ) {
        return ResponseEntity.ok(supportService.answerForTicket(customUserDetail, id, ticketAnswerBodyDTO));
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_admin')")
    @GetMapping("/tickets")
    public ResponseEntity<List<TicketDTO>> getAvailableTicket() {
        return ResponseEntity.ok(supportService.getAvailableTicket());
    }
}
