package keenay.education.service;

import keenay.education.dto.chat.ChatMessageBodyDTO;

import java.security.Principal;

public interface MessageService {
    void newMessage(ChatMessageBodyDTO chatMessageBodyDTO, Principal principal);
}
