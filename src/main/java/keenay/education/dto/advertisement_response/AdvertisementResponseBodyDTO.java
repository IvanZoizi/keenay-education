package keenay.education.dto.advertisement_response;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class AdvertisementResponseBodyDTO {
    @NonNull
    private Long advertisementId;
    @NonNull
    @Min(value = 500)
    @Max(value = 100000)
    private Integer price;
    @NonNull
    @Size(min = 50, max = 200)
    private String comment;
}
