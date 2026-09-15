package keenay.education.dto.advertisement_response;

import keenay.education.entity.status.AdvertisementResponseStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdvertisementResponseDTO {
    private Long id;
    private Long advertisementId;
    private Integer price;
    private String comment;
    private AdvertisementResponseStatus status;
}
