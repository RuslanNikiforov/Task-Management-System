package nikiforov.ruslan.taskmanagementsystem.repositories;

import nikiforov.ruslan.taskmanagementsystem.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByAuthorId(long authorId);

    List<Task> findAllByExecutorsId(long executorId);
}