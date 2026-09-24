package keenay.education.controller;

import keenay.education.controllers.http.impl.ChatControllerImpl;
import keenay.education.dto.chat.ChatDTO;
import keenay.education.dto.chat.MessageDTO;
import keenay.education.security.CustomUserDetail;
import keenay.education.service.ChatService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatControllerImplTest {

    private static final Long AD_ID = 20L;
    private static final Long RESPONSE_ID = 30L;
    private static final Long CHAT_ID = 10L;

    @Mock
    private ChatService chatService;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private CustomUserDetail userDetail;

    @InjectMocks
    private ChatControllerImpl chatController;

    private ChatDTO chatDto(Long id) {
        ChatDTO dto = new ChatDTO();
        dto.setChatId(id);
        dto.setSellerId(3L);
        dto.setCustomerId(2L);
        return dto;
    }

    private MessageDTO messageDto(Long id) {
        MessageDTO dto = new MessageDTO();
        dto.setId(id);
        dto.setChatId(CHAT_ID);
        dto.setSenderId(1L);
        dto.setRole("ROLE_customer");
        dto.setText("Text");
        return dto;
    }

    @Test
    @DisplayName("Тест createChat")
    public void testCreateChatSuccess() {
        ChatDTO expected = chatDto(CHAT_ID);
        when(chatService.createChat(userDetail, RESPONSE_ID, AD_ID)).thenReturn(expected);

        ResponseEntity<ChatDTO> response = chatController.createChat(userDetail, AD_ID, RESPONSE_ID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(chatService).createChat(userDetail, RESPONSE_ID, AD_ID);
    }

    @Test
    @DisplayName("Тест getChat")
    public void testGetChatSuccess() {
        ChatDTO expected = chatDto(CHAT_ID);
        when(chatService.getChat(userDetail, CHAT_ID)).thenReturn(expected);

        ResponseEntity<ChatDTO> response = chatController.getChat(userDetail, CHAT_ID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(chatService).getChat(userDetail, CHAT_ID);
    }

    @Test
    @DisplayName("Тест getChats")
    public void testGetChatsSuccess() {
        List<ChatDTO> expected = List.of(chatDto(1L), chatDto(2L));
        when(chatService.getChats(userDetail)).thenReturn(expected);

        ResponseEntity<List<ChatDTO>> response = chatController.getChats(userDetail);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getChatId());
        assertEquals(2L, response.getBody().get(1).getChatId());
        verify(chatService).getChats(userDetail);
    }

    @Test
    @DisplayName("Тест getChats пустой список")
    public void testGetChatsEmpty() {
        when(chatService.getChats(userDetail)).thenReturn(List.of());

        ResponseEntity<List<ChatDTO>> response = chatController.getChats(userDetail);

        assertNotNull(response);
        assertTrue(response.getBody().isEmpty());
        verify(chatService).getChats(userDetail);
    }

    @Test
    @DisplayName("Тест getMessages")
    public void testGetMessagesSuccess() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<MessageDTO> expected = new PageImpl<>(List.of(messageDto(1L), messageDto(2L)), pageable, 2);

        when(chatService.getMessages(userDetail, CHAT_ID, pageable)).thenReturn(expected);

        Page<MessageDTO> response = chatController.getMessages(CHAT_ID, userDetail, pageable);

        assertNotNull(response);
        assertEquals(2, response.getContent().size());
        assertEquals(1L, response.getContent().get(0).getId());
        assertEquals(2L, response.getContent().get(1).getId());
        verify(chatService).getMessages(userDetail, CHAT_ID, pageable);
    }

    @Test
    @DisplayName("Тест getMessages пустая страница")
    public void testGetMessagesEmpty() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<MessageDTO> expected = Page.empty(pageable);

        when(chatService.getMessages(userDetail, CHAT_ID, pageable)).thenReturn(expected);

        Page<MessageDTO> response = chatController.getMessages(CHAT_ID, userDetail, pageable);

        assertNotNull(response);
        assertTrue(response.isEmpty());
        verify(chatService).getMessages(userDetail, CHAT_ID, pageable);
    }
}