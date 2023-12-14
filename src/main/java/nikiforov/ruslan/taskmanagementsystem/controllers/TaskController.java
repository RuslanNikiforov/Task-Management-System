package nikiforov.ruslan.taskmanagementsystem.controllers;

import nikiforov.ruslan.taskmanagementsystem.dto.TaskDto;
import nikiforov.ruslan.taskmanagementsystem.entity.Task;
import nikiforov.ruslan.taskmanagementsystem.entity.User;
import nikiforov.ruslan.taskmanagementsystem.model.Status;
import nikiforov.ruslan.taskmanagementsystem.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    public final int userId = 1;
    private TaskService taskService;

    public TaskController(@Autowired TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAll() {
        List<Task> tasks = taskService.getAll();
        if(tasks != null) {
            return ResponseEntity.ok(tasks);
        }
        else {
            return ResponseEntity.status(401).build();
        }
    }

    public ResponseEntity<List<Task>> getAllByAuthor(long userId) {
        List<Task> tasks = taskService.getAllByAuthor(userId);
        if(tasks != null) {
            return ResponseEntity.ok(tasks);
        }
        else {
            return ResponseEntity.status(401).build();
        }
    }

    public ResponseEntity<List<Task>> getAllByExecutor(long userId) {
        List<Task> tasks = taskService.getAllByExecutor(userId);
        if(tasks != null) {
            return ResponseEntity.ok(tasks);
        }
        else {
            return ResponseEntity.status(401).build();
        }
    }

    @ResponseBody
    @PostMapping("/create")
    public ResponseEntity<Task> create(@RequestBody Task task) {
        Task newTask = taskService.create(1, task);
        if(newTask != null) {
            System.out.println(newTask);
            return ResponseEntity.ok(newTask);
        }
        else {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/update/{taskId}")
    public ResponseEntity<Task> update(@PathVariable(value = "taskId") Long taskId, @RequestBody Task task) {
        Task updated = taskService.update(userId, taskId, task);
        if(updated != null) {
            return ResponseEntity.ok(updated);
        }
        else {
            return ResponseEntity.status(401).build();
        }
    }

    /*@DeleteMapping("/delete{taskId}")
    public ResponseEntity<Boolean> delete(long userId, @PathVariable Integer taskId) {
        List<Task> tasks = taskService.delete(userId, taskId);
        if(tasks != null) {
            return ResponseEntity.ok(tasks);
        }
        else {
            return ResponseEntity.status(401).build();
        }
    }*/

    @PostMapping("/changeStatus/{taskId}")
    public ResponseEntity<Task> changeStatus(@PathVariable Integer taskId, @RequestParam String status) {
        Task task = taskService.changeStatus(4, taskId, Status.valueOf(status));
        if(task != null) {
            return ResponseEntity.ok(task);
        }
        else {
            return ResponseEntity.status(401).build();
        }
    }

    public ResponseEntity<Task> setExecutors(long userId, @PathVariable Long taskId, @RequestBody List<User> users) {
        Task task = taskService.setExecutors(userId, taskId, users);
        if(task != null) {
            return ResponseEntity.ok(task);
        }
        else {
            return ResponseEntity.status(401).build();
        }
    }
}
