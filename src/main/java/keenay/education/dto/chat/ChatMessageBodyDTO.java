package keenay.education.dto.chat;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ChatMessageBodyDTO {
    @NonNull
    private Long chatId;
    @NonNull
    private String text;
}
