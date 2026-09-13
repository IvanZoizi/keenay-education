package keenay.education.dto.tasks;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class TaskBodyDTO {
    @NonNull
    private String title;
    @NonNull
    private String description;
}
