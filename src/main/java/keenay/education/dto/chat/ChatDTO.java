package keenay.education.dto.chat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatDTO {
    private Long chatId;
    private Long sellerId;
    private Long customerId;
}
