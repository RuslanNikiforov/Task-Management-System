package nikiforov.ruslan.taskmanagementsystem.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommentRequest implements Serializable {

    private String text;

    public CommentRequest() {
    }
}
