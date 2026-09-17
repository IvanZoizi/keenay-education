package keenay.education.service;

import keenay.education.dto.chat.ChatDTO;
import keenay.education.dto.chat.MessageDTO;
import keenay.education.security.CustomUserDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ChatService {
    ChatDTO createChat(CustomUserDetail customUserDetail, Long advertisementResponseId, Long advertisementId);
    ChatDTO getChat(CustomUserDetail customUserDetail, Long chatId);
    List<ChatDTO> getChats(CustomUserDetail customUserDetail);
    Page<MessageDTO> getMessages(CustomUserDetail customUserDetail, Long chatId, Pageable pageable);
}
