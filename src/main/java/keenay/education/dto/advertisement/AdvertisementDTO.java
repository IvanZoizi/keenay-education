package keenay.education.dto.advertisement;

import keenay.education.dto.pets.PetsDTO;
import keenay.education.dto.tasks.TaskDTO;
import keenay.education.entity.status.AdvertisementStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AdvertisementDTO {
    private Long id;
    private Long selectedResponse = null;
    private PetsDTO petsDTO;
    private Integer budget;
    private AdvertisementStatus advertisementStatus;
    private List<TaskDTO> taskDTOS;
}
