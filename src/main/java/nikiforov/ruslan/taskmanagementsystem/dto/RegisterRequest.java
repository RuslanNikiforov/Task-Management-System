package nikiforov.ruslan.taskmanagementsystem.dto;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link nikiforov.ruslan.taskmanagementsystem.entity.User}
 */
@Data
@Builder
public class RegisterRequest implements Serializable {

    private String email;

    private String password;

    private String name;
}