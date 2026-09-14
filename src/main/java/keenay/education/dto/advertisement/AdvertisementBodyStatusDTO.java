package keenay.education.dto.advertisement;

import keenay.education.entity.status.AdvertisementStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementBodyStatusDTO {
    private AdvertisementStatus status;
}
