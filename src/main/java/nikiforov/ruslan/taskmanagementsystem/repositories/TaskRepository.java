package nikiforov.ruslan.taskmanagementsystem.repositories;

import jakarta.transaction.Transactional;
import nikiforov.ruslan.taskmanagementsystem.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByAuthorId(long authorId);

    List<Task> findAllByExecutorsId(long executorId);

    @Transactional
    @Modifying
    @Query("DELETE Task task where task.id = ?1 and task.author.id = ?2")
    int deleteTask(long taskId, long userId);
}