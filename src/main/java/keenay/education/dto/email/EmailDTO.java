package keenay.education.dto.email;

import keenay.education.entity.EmailsUser;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailDTO {
    private Long userId;
    private Long emailsUserId;
    private String email;
    private String text;
    private String subject;
    private Integer attempt;
    private Boolean isSend = false;
    private Throwable ex = null;
}
