package keenay.education.mapper.chat;

import keenay.education.dto.animal.AnimalDTO;
import keenay.education.dto.chat.ChatDTO;
import keenay.education.dto.chat.MessageDTO;
import keenay.education.entity.Animals;
import keenay.education.entity.Chat;
import keenay.education.entity.Messages;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    @Mappings({
            @Mapping(source = "chat.id", target = "chatId"),
            @Mapping(source = "chat.customer.id", target = "customerId"),
            @Mapping(source = "chat.seller.id", target = "sellerId")
    })
    ChatDTO getDTO(Chat chat);

    @Mappings({
            @Mapping(source = "message.id", target = "id"),
            @Mapping(source = "message.chat.id", target = "chatId"),
            @Mapping(source = "message.sender.id", target = "senderId"),
            @Mapping(source = "message.role.role", target = "role"),
            @Mapping(source = "message.text", target = "text")
    })
    MessageDTO getDTO(Messages message);
}
