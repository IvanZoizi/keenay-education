package keenay.education.controllers.ws;

import keenay.education.dto.chat.ChatMessageBodyDTO;
import keenay.education.service.ChatService;
import keenay.education.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatControllerWS {
    private final MessageService messageService;

    @MessageMapping("chat")
    public void processMessage(@Payload ChatMessageBodyDTO chatMessageBodyDTO,
                               Principal principal) {
        messageService.newMessage(chatMessageBodyDTO, principal);
    }
}
