package nikiforov.ruslan.taskmanagementsystem.services;

import nikiforov.ruslan.taskmanagementsystem.dto.TaskRequest;
import nikiforov.ruslan.taskmanagementsystem.dto.TaskToUpdateRequest;
import nikiforov.ruslan.taskmanagementsystem.entity.Task;
import nikiforov.ruslan.taskmanagementsystem.entity.User;
import nikiforov.ruslan.taskmanagementsystem.model.Priority;
import nikiforov.ruslan.taskmanagementsystem.model.Status;
import nikiforov.ruslan.taskmanagementsystem.repositories.TaskRepository;
import nikiforov.ruslan.taskmanagementsystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    private UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public Task getById(long taskId) {
        return taskRepository.getReferenceById(taskId);
    }


    public List<Task> getAllByAuthor(long userId) {
        return taskRepository.findAllByAuthorId(userId);
    }

    public List<Task> getAllByExecutor(long userId) {
        return taskRepository.findAllByExecutorsId(userId);
    }

    public Task create(long userId, TaskRequest newTask) {
        Task task = Task.builder().header(newTask.getHeader())
                .description(newTask.getDescription())
                .status(Status.valueOf(newTask.getStatus().toUpperCase()))
                .priority(Priority.valueOf(newTask.getPriority().toUpperCase())).build();
        task.setAuthor(userRepository.getReferenceById(userId));
        return taskRepository.save(task);
    }

    public Task update(long userId, long taskId, TaskToUpdateRequest task) {
        Task existing = taskRepository.getReferenceById(taskId);
        if(taskId == task.getId() && existing.getAuthor().getId() == userId) {
            existing.setPriority(Priority.valueOf(task.getPriority().toUpperCase()));
            existing.setHeader(task.getHeader());
            existing.setStatus(Status.valueOf(task.getStatus().toUpperCase()));
            existing.setDescription(task.getDescription());
            return taskRepository.save(existing);
        }
        else {
            return null;
        }
    }

    public boolean delete(long userId, long taskId) {
        Task task = getById(taskId);
        if(!task.isNew() & task.getAuthor().getId() == userId) {
             int result = taskRepository.deleteTask(taskId, userId);
            return result == 1;
        }
        else {
            return false;
        }
    }

    public Task changeStatus(long userId, long taskId, Status status) {
        Task referenceById = taskRepository.getReferenceById(taskId);
        referenceById.setStatus(status);
        return taskRepository.save(referenceById);

    }


    public Task setExecutors(long authorId, long taskId, List<Long> users) {
        Task referenceById = taskRepository.getReferenceById(taskId);
        if(authorId == referenceById.getAuthor().getId()) {
            List<User> executors = users.stream().map(el -> userRepository.getReferenceById(el)).
                    collect(Collectors.toList());
            referenceById.setExecutors(executors);
            return taskRepository.save(referenceById);
        }
        return null;
    }

    public List<Task> getAll(String header, String description, String priority, String status, String authorName,
                             String page, String numberElementsOnPage) {
        int currentPage = Integer.parseInt(page);
        int elementsOnPage = Integer.parseInt(numberElementsOnPage);
        List<Task> tasks = getAll();
        List<Task> list = tasks.stream().filter(task -> task.getHeader().toUpperCase().contains(header.toUpperCase())
                && task.getDescription().toUpperCase().contains(description.toUpperCase()) &&
                task.getAuthor().getName().toUpperCase().contains(authorName.toUpperCase())).toList();
        if(!priority.isEmpty()) {
            list = list.stream().filter(task-> task.getPriority().name().equals(priority.toUpperCase())).toList();
        }
        if(!status.isEmpty()) {
            list = list.stream().filter(task -> task.getStatus().name().equals(status.toUpperCase())).toList();
        }
                return list.stream().skip((long) (currentPage - 1) * elementsOnPage).limit(elementsOnPage).toList();


    }

}
