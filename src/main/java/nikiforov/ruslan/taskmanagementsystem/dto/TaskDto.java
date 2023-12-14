package nikiforov.ruslan.taskmanagementsystem.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nikiforov.ruslan.taskmanagementsystem.model.Priority;
import nikiforov.ruslan.taskmanagementsystem.model.Status;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private String header;

    private String description;

    private Priority priority;
}
