package nikiforov.ruslan.taskmanagementsystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import nikiforov.ruslan.taskmanagementsystem.dto.ArrayRequest;
import nikiforov.ruslan.taskmanagementsystem.dto.CommentRequest;
import nikiforov.ruslan.taskmanagementsystem.dto.TaskRequest;
import nikiforov.ruslan.taskmanagementsystem.dto.TaskToUpdateRequest;
import nikiforov.ruslan.taskmanagementsystem.entity.Comment;
import nikiforov.ruslan.taskmanagementsystem.entity.Task;
import nikiforov.ruslan.taskmanagementsystem.entity.User;
import nikiforov.ruslan.taskmanagementsystem.model.Status;
import nikiforov.ruslan.taskmanagementsystem.services.CommentService;
import nikiforov.ruslan.taskmanagementsystem.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "tmsapi")
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;

    private CommentService commentService;

    @Autowired
    public TaskController(TaskService taskService, CommentService commentService) {
        this.commentService = commentService;
        this.taskService = taskService;
    }

    @Operation(summary = "Get tasks with filters and pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found tasks or result list is empty",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))}),
            @ApiResponse(responseCode = "403", description = "Not Authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Result list is null",
                    content = @Content)})
    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAll(@RequestParam(required = false, defaultValue = "") String header,
                                             @RequestParam(required = false, defaultValue = "") String description,
                                             @RequestParam(required = false, defaultValue = "") String priority,
                                             @RequestParam(required = false, defaultValue = "") String status,
                                             @RequestParam(required = false, defaultValue = "") String authorName,
                                             @RequestParam(required = false, defaultValue = "1") String page,
                                             @RequestParam(required = false, defaultValue = "8") String numberElementsOnPage) {
        List<Task> tasks = taskService.getAll(header, description, priority, status, authorName, page,
                numberElementsOnPage);
        if (tasks != null) {
            return ResponseEntity.ok(tasks);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get all tasks by author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found tasks",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))}),
            @ApiResponse(responseCode = "403", description = "Not Authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Result list is empty or null",
                    content = @Content)})
    @GetMapping("/all/{authorId}")
    public ResponseEntity<List<Task>> getAllByAuthor(@PathVariable long authorId) {
        List<Task> tasks = taskService.getAllByAuthor(authorId);
        if (tasks != null && !tasks.isEmpty()) {
            return ResponseEntity.ok(tasks);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get all tasks by current authorized user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found tasks or if user has no tasks",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))}),
            @ApiResponse(responseCode = "403", description = "Not Authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Result list is null",
                    content = @Content)})
    @GetMapping("/all/my")
    public ResponseEntity<List<Task>> getAllByAuthorizedUser(@AuthenticationPrincipal User user) {
        List<Task> tasks = taskService.getAllByAuthor(user.getId());
        if (tasks != null) {
            return ResponseEntity.ok(tasks);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get all tasks by executor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found tasks",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))}),
            @ApiResponse(responseCode = "403", description = "Not Authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Result list is empty or null",
                    content = @Content)})
    @GetMapping("/all/getByExecutor/{executorId}")
    public ResponseEntity<List<Task>> getAllByExecutor(@PathVariable long executorId) {
        List<Task> tasks = taskService.getAllByExecutor(executorId);
        if (tasks != null && !tasks.isEmpty()) {
            return ResponseEntity.ok(tasks);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Create new task by given request body")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation =
                    TaskRequest.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task has been created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))}),
            @ApiResponse(responseCode = "403", description = "Not Authorized",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid task in request body",
                    content = @Content)})
    @ResponseBody
    @PostMapping("/create")
    public ResponseEntity<Task> create(@AuthenticationPrincipal User user, @RequestBody TaskRequest task) {

        if (task != null) {
            Task newTask = taskService.create(user.getId(), task);
            return ResponseEntity.ok(newTask);
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Update task by given request body")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation =
                    TaskToUpdateRequest.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task has been updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))}),
            @ApiResponse(responseCode = "403", description = "Not Authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task id in request body and taskId in path variable " +
                    "doesn't match",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid task in request body",
                    content = @Content)})
    @PostMapping("/update/{taskId}")
    public ResponseEntity<Task> update(@AuthenticationPrincipal User user, @PathVariable(value = "taskId") Long taskId,
                                       @RequestBody TaskToUpdateRequest task) {
        if (task != null) {
            Task updated = taskService.update(user.getId(), taskId, task);
            if (updated != null) {
                return ResponseEntity.ok(updated);
            } else {
                return ResponseEntity.status(404).build();
            }
        }
        return ResponseEntity.badRequest().build();

    }

    @Operation(summary = "Delete task by taskId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task has been successfully deleted!",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))}),
            @ApiResponse(responseCode = "403", description = "Not Authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task was not found to be deleted or user is trying to " +
                    "delete a task that is not his own",
                    content = @Content)})
    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<String> delete(@AuthenticationPrincipal User user, @PathVariable Integer taskId) {
        boolean isDeleted = taskService.delete(user.getId(), taskId);
        if (isDeleted) {
            return ResponseEntity.ok("Task has been successfully deleted!");
        } else {
            return ResponseEntity.status(404).body("Task was not found to be deleted or user is trying to delete" +
                    " a task that is not his own");
        }
    }

    @Operation(summary = "Change the status of a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status of a task has been successfully changed!",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))}),
            @ApiResponse(responseCode = "403", description = "Not Authorized or current authenticated user can't update" +
                    " status of this task because he's not the author or executor",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task with this id wasn't found",
                    content = @Content)})
    @PostMapping("/changeStatus/{taskId}")
    public ResponseEntity<Task> changeStatus(@AuthenticationPrincipal User user, @PathVariable Integer taskId,
                                             @RequestParam String status) {
        Task taskToUpdateStatus = taskService.getById(taskId);
        if (taskToUpdateStatus == null) {
            return ResponseEntity.status(404).build();
        }
        if ((taskToUpdateStatus.getAuthor().equals(user) || taskToUpdateStatus.getExecutors().contains(user))) {
            Task changedTask = taskService.changeStatus(user.getId(), taskId, Status.valueOf(status));
            return ResponseEntity.ok(changedTask);
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @Operation(summary = "Set executors to this task")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = ArrayRequest.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Executors have been successfully set!",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))}),
            @ApiResponse(responseCode = "403", description = "Not Authorized or current authenticated user is not" +
                    "the author of this task",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task with this id wasn't found",
                    content = @Content)})
    @PostMapping("/setExecutors/{taskId}")
    public ResponseEntity<Task> setExecutors(@AuthenticationPrincipal User user, @PathVariable Integer taskId,
                                             @RequestBody ArrayRequest usersId) {
        try {
            Task task = taskService.setExecutors(user.getId(), taskId, usersId.getArray());
            if (task != null) {
                return ResponseEntity.ok(task);
            } else {
                return ResponseEntity.status(403).build();
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @Operation(summary = "Send comment to this task")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = CommentRequest.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment to this task has been successfully sent!",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Comment.class))}),
            @ApiResponse(responseCode = "403", description = "Not Authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task with this id wasn't found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid comment in request body",
                    content = @Content)})
    @PostMapping("/sendComment/{taskId}")
    public ResponseEntity<Comment> sendComment(@AuthenticationPrincipal User user, @PathVariable Long taskId,
                                               @RequestBody CommentRequest commentReq) {
        if (commentReq != null) {
            try {
                Task task = taskService.getById(taskId);
                Comment comment = commentService.saveComment(user, task, commentReq);
                return ResponseEntity.ok(comment);
            } catch (EntityNotFoundException e) {
                return ResponseEntity.status(404).build();
            }
        }
        return ResponseEntity.status(400).build();
    }

    @Operation(summary = "Delete comment from task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment has been successfully deleted!",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "403", description = "Not Authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found comment with this id",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid taskId format",
                    content = @Content)})
    @DeleteMapping("/deleteComment/{commentId}")
    public ResponseEntity<String> sendComment(@AuthenticationPrincipal User user, @PathVariable Long commentId,
                                              @RequestParam String taskId) {
        try {
            int parsedTaskId = Integer.parseInt(taskId);
            Boolean isDeleted = commentService.deleteComment(commentId, user.getId(), parsedTaskId);
            if (isDeleted == null) {
                return ResponseEntity.status(403).body("You can't delete comment that you don't own or " +
                        "delete it form other task");
            }
            if (isDeleted) {
                return ResponseEntity.ok().body("Comment has been successfully deleted!");
            } else {
                return ResponseEntity.status(500).body("Unexpected internal error");
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body("Not found comment with this id");
        } catch (NumberFormatException e) {
            return ResponseEntity.status(400).body("Invalid taskId");
        }
    }
}
