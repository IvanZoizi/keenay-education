package keenay.education.dto.pets;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class PetsBodyDTO {
    @NonNull
    private String nameAnimal;
    @NonNull
    private String namePet;
    @NonNull
    private String breed;
    @NonNull
    private String features;
    @NonNull
    private String vaccinations;
}
