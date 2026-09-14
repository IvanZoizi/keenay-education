package keenay.education.dto.tasks;

import keenay.education.entity.status.TasksStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class TaskBodyStatusDTO
{
    @NonNull
    private TasksStatus status;
}
