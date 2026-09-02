package keenay.education.dto.support;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TicketWithAnswerDTO {
    private Long id;
    private String question;
    private String answer;
}
