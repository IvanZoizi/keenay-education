package keenay.education.dto.support;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class TicketBodyDTO {
    @NonNull
    private String question;
}
