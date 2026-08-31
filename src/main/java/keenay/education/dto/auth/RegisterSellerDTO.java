package keenay.education.dto.auth;

import jakarta.validation.constraints.Size;
import keenay.education.validation.EmailValid;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class RegisterSellerDTO {
    @EmailValid
    @NonNull
    private String email;
    @NonNull
    @Size(min = 8, max = 20)
    private String password;
    @NonNull
    private String name;
    @NonNull
    private String surname;
    @NonNull
    private String address;
    @NonNull
    @Size(min = 10, max = 12)
    private String inn;
    @NonNull
    private String description;
}
