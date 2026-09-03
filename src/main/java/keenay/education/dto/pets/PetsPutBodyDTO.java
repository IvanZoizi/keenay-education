package keenay.education.dto.pets;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class PetsPutBodyDTO {
    @NonNull
    private String breed;
    @NonNull
    private String features;
    @NonNull
    private String vaccinations;
}
