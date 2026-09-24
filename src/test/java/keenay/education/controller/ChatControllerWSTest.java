package keenay.education.controller;

import keenay.education.controllers.ws.ChatControllerWS;
import keenay.education.dto.chat.ChatMessageBodyDTO;
import keenay.education.service.MessageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatControllerWSTest {

    private static final Long CHAT_ID = 10L;

    @Mock
    private MessageService messageService;

    @Mock
    private Principal principal;

    @InjectMocks
    private ChatControllerWS chatControllerWS;

    private ChatMessageBodyDTO body() {
        ChatMessageBodyDTO body = new ChatMessageBodyDTO();
        body.setChatId(CHAT_ID);
        body.setText("Text");
        return body;
    }

    @Test
    @DisplayName("Тест processMessage")
    public void testProcessMessageSuccess() {
        ChatMessageBodyDTO body = body();

        chatControllerWS.processMessage(CHAT_ID, body, principal);

        verify(messageService).newMessage(eq(CHAT_ID), eq(body), eq(principal));
    }

    @Test
    @DisplayName("Тест processMessage с пустым телом")
    public void testProcessMessageEmptyBody() {
        ChatMessageBodyDTO body = new ChatMessageBodyDTO();
        body.setChatId(CHAT_ID);
        body.setText("");

        chatControllerWS.processMessage(CHAT_ID, body, principal);

        verify(messageService).newMessage(eq(CHAT_ID), eq(body), eq(principal));
    }

    @Test
    @DisplayName("Тест processMessage без Principal")
    public void testProcessMessageNullPrincipal() {
        ChatMessageBodyDTO body = body();

        chatControllerWS.processMessage(CHAT_ID, body, null);

        verify(messageService).newMessage(eq(CHAT_ID), eq(body), isNull());
    }
}