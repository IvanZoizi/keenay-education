package keenay.education.mapper.advertisement;

import keenay.education.dto.advertisement.AdvertisementDTO;
import keenay.education.dto.tasks.TaskDTO;
import keenay.education.entity.Advertisement;
import keenay.education.entity.Pets;
import keenay.education.entity.Tasks;
import keenay.education.mapper.pets.PetsMapper;
import keenay.education.mapper.tasks.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AdvertisementMapper {

    private final PetsMapper petsMapper;
    private final TaskMapper taskMapper;

    public AdvertisementDTO getDTO(Advertisement advertisement) {
        AdvertisementDTO advertisementDTO = new AdvertisementDTO();
        advertisementDTO.setId(advertisement.getId());
        advertisementDTO.setPetsDTO(petsMapper.getPets(advertisement.getPet()));
        advertisementDTO.setAdvertisementStatus(advertisement.getStatus());
        advertisementDTO.setBudget(advertisement.getBudget());
        if (advertisement.getSelectedResponse() != null) {
            advertisementDTO.setSelectedResponse(advertisement.getSelectedResponse().getId());
        }
        List<TaskDTO> tasksList = new ArrayList<>();
        System.out.println(advertisement.getTasksList().size());
        for (Tasks task : advertisement.getTasksList()) {
            tasksList.add(taskMapper.getDTO(task));
        }
        advertisementDTO.setTaskDTOS(tasksList);
        return advertisementDTO;
    }
}
