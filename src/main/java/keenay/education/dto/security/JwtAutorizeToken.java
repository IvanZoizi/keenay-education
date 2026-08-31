package keenay.education.dto.security;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtAutorizeToken {
    private String token;
    private String refreshToken;
}
