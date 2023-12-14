package nikiforov.ruslan.taskmanagementsystem.repositories;

import nikiforov.ruslan.taskmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}