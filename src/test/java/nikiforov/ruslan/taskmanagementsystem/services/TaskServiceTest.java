package nikiforov.ruslan.taskmanagementsystem.services;

import jakarta.transaction.Transactional;

import nikiforov.ruslan.taskmanagementsystem.data.TaskData;
import nikiforov.ruslan.taskmanagementsystem.dto.CommentRequest;
import nikiforov.ruslan.taskmanagementsystem.dto.TaskRequest;
import nikiforov.ruslan.taskmanagementsystem.dto.TaskToUpdateRequest;
import nikiforov.ruslan.taskmanagementsystem.entity.Comment;
import nikiforov.ruslan.taskmanagementsystem.entity.Task;
import nikiforov.ruslan.taskmanagementsystem.entity.User;
import nikiforov.ruslan.taskmanagementsystem.model.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;
import static nikiforov.ruslan.taskmanagementsystem.data.TaskData.*;
import static nikiforov.ruslan.taskmanagementsystem.data.UserData.*;


@SpringBootTest
@Sql(scripts = {"classpath:create.sql", "classpath:init.sql"})
class TaskServiceTest {

    @Autowired
    TaskService taskService;

    @Autowired
    CommentService commentService;

    @Order(1)
    @Transactional
    @Test
    void getAll() {
        Assertions.assertEquals(tasks, taskService.getAll());
    }

    @Order(2)
    @Transactional
    @Test
    void getAllFiltered() {
        Assertions.assertEquals(List.of(task3), taskService.getAll("3", "dscr3", "MEDIUM",
                "WAITING", user2.getName(), "1", "1"));
    }
    @Order(3)
    @Transactional
    @Test
    void getAllByAuthor() {
        Assertions.assertEquals(List.of(task3, task4), taskService.getAllByAuthor(102));
    }

    @Order(4)
    @Test
    void getAllByExecutor() {
        Assertions.assertEquals(List.of(task1, task2), taskService.getAllByExecutor(102));
    }

    @Test
    @Transactional
    void delete() {
        Assertions.assertTrue(taskService.delete(101, 102));

    }

    @Transactional
    @Test
    void create() {
        Task task = TaskData.getNew();
        Task created = taskService.create(101, TaskRequest.builder()
                .description(task.getDescription())
                .header(task.getHeader())
                .status(String.valueOf(task.getStatus()))
                .priority(String.valueOf(task.getPriority())).build());
        task.setId(created.getId());
        Assertions.assertEquals(task, created);
    }

    @Transactional
    @Test
    void update() {
        Task existing = TaskData.getExisting();
        existing.setHeader("Make docs");
        TaskToUpdateRequest request = TaskToUpdateRequest.builder().id(Math.toIntExact(existing.getId())).status(String.valueOf(existing.getStatus()).toUpperCase())
                .priority(String.valueOf(existing.getPriority()).toUpperCase())
                .description(existing.getDescription()).header(existing.getHeader()).build();
        Assertions.assertEquals(existing, taskService.update(existing.getAuthor().getId(), existing.getId(), request));


    }

    @Test
    @Transactional
    void changeStatus() {
        Task existing = task1;
        existing.setStatus(Status.IN_PROGRESS);
        Assertions.assertEquals(existing, taskService.changeStatus(existing.getAuthor().getId(), existing.getId(),
                Status.IN_PROGRESS));
    }

    @Test
    @Transactional
    void setExecutors() {
        Task existing = task4;
        existing.setExecutors(List.of(user2, user3));
        Assertions.assertEquals(existing, taskService.setExecutors(existing.getAuthor().getId(), existing.getId(),
                List.of(102L, 103L)));

    }

    @Test
    @Transactional
    void sendComment() {
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setText("newComment");
        Comment actual = commentService.saveComment(user1, task2, commentRequest);
        Comment expected = Comment.builder().id(actual.getId()).text(commentRequest.getText()).
                timeSent(actual.getTimeSent()).build();
        Assertions.assertEquals(expected, actual);

    }


}