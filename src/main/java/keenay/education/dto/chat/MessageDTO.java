package keenay.education.dto.chat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageDTO {
    private Long id;
    private Long chatId;
    private Long senderId;
    private String role;
    private String text;
}
