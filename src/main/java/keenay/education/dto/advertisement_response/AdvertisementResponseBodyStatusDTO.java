package keenay.education.dto.advertisement_response;

import keenay.education.entity.status.AdvertisementResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementResponseBodyStatusDTO {
    @NonNull
    private AdvertisementResponseStatus status;
}
