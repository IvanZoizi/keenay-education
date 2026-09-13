package keenay.education.dto.tasks;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskDTO {
    private String title;
    private String description;
    private String photoUrl = null;
    private String idAdvertisement = null;
}
