package nikiforov.ruslan.taskmanagementsystem.dto;

import lombok.*;
import nikiforov.ruslan.taskmanagementsystem.model.Priority;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest implements Serializable {

    private String header;

    private String description;

    private String priority;

    private String status;
}
