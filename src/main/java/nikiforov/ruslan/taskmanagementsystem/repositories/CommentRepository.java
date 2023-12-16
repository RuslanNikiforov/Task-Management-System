package nikiforov.ruslan.taskmanagementsystem.repositories;

import jakarta.transaction.Transactional;
import nikiforov.ruslan.taskmanagementsystem.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> getAllByTaskId(long taskId);

    @Transactional
    @Modifying
    @Query("DELETE Comment comment where comment.id = ?1 and comment.user.id = ?2 and comment.task.id = ?3")
    int deleteTask(long commentId, long userId, long taskId);
}