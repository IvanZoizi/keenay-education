package keenay.education.dto.advertisement;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
public class AdvertisementBodyDTO {
    @NonNull
    private Long petId;
    @NonNull
    @Min(value = 500)
    @Max(value = 100000)
    private Integer budget;
    @NonNull
    private List<Long> listTasksId;
}
