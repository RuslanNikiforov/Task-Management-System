package nikiforov.ruslan.taskmanagementsystem.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class TaskToUpdateRequest implements Serializable {

    private int id;

    private String header;

    private String description;

    private String priority;

    private String status;
}
