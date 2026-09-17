package keenay.education.controllers.http.impl;

import keenay.education.controllers.http.ChatController;
import keenay.education.dto.advertisement.AdvertisementBodyDTO;
import keenay.education.dto.advertisement.AdvertisementDTO;
import keenay.education.dto.chat.ChatDTO;
import keenay.education.dto.chat.MessageDTO;
import keenay.education.security.CustomUserDetail;
import keenay.education.service.AdvertisementService;
import keenay.education.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatControllerImpl implements ChatController {
    private final ChatService chatService;

    @PostMapping("/advertisement/{advertisementId}/response/{responseId}")
    @PreAuthorize("hasAuthority('ROLE_customer')")
    public ResponseEntity<ChatDTO> createChat(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable("advertisementId") Long advertisementId,
            @PathVariable("responseId") Long responseId
    ) {
        return ResponseEntity.ok(chatService.createChat(userDetail, responseId, advertisementId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatDTO> getChat(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable("id") Long chatId
    ) {
        return ResponseEntity.ok(chatService.getChat(userDetail, chatId));
    }

    @GetMapping
    public ResponseEntity<List<ChatDTO>> getChats(
            @AuthenticationPrincipal CustomUserDetail userDetail
    ) {
        return ResponseEntity.ok(chatService.getChats(userDetail));
    }

    @GetMapping("/{chatId}/messages")
    public Page<MessageDTO> getMessages(@PathVariable("chatId") Long chatId,
                                        @AuthenticationPrincipal CustomUserDetail user,
                                        Pageable pageable) {
        return chatService.getMessages(user, chatId, pageable);
    }
}
