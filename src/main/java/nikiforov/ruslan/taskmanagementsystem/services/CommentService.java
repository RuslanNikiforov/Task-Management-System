package nikiforov.ruslan.taskmanagementsystem.services;

import nikiforov.ruslan.taskmanagementsystem.dto.CommentRequest;
import nikiforov.ruslan.taskmanagementsystem.entity.Comment;
import nikiforov.ruslan.taskmanagementsystem.entity.Task;
import nikiforov.ruslan.taskmanagementsystem.entity.User;
import nikiforov.ruslan.taskmanagementsystem.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment saveComment(User user, Task task, CommentRequest comment) {
        Comment newComment = Comment.builder().
                text(comment.getText())
                .task(task)
                .user(user)
                .timeSent(LocalDateTime.now())
                .build();
        return commentRepository.save(newComment);
    }


    public Boolean deleteComment(long commentId, long userId, long taskId) {
        Comment referenceById = commentRepository.getReferenceById(commentId);
        if(referenceById.getTask().getId() == taskId && referenceById.getUser().getId() == userId) {
            return commentRepository.deleteTask(commentId, userId, taskId) == 1;
        }
        return null;
    }
}
