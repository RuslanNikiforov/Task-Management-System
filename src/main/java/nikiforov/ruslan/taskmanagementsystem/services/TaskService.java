package nikiforov.ruslan.taskmanagementsystem.services;

import nikiforov.ruslan.taskmanagementsystem.dto.TaskDto;
import nikiforov.ruslan.taskmanagementsystem.entity.Task;
import nikiforov.ruslan.taskmanagementsystem.entity.User;
import nikiforov.ruslan.taskmanagementsystem.model.Priority;
import nikiforov.ruslan.taskmanagementsystem.model.Status;
import nikiforov.ruslan.taskmanagementsystem.repositories.TaskRepository;
import nikiforov.ruslan.taskmanagementsystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


    public List<Task> getAllByAuthor(long userId) {
        return taskRepository.findAllByAuthorId(userId);
    }

    public List<Task> getAllByExecutor(long userId) {
        return taskRepository.findAllByExecutorsId(userId);
    }

    public Task create(long userId, Task newTask) {
        /*Task newTask = new Task();
        newTask.setHeader(task.getHeader());
        newTask.setDescription(task.getDescription());
        newTask.setPriority(task.getPriority());*/
        newTask.setAuthor(userRepository.getReferenceById(userId));
        newTask.setStatus(Status.WAITING);
        newTask.setExecutors(List.of(newTask.getAuthor()));

        return taskRepository.save(newTask);
    }

    public Task update(long userId, long taskId, Task task) {
        Task existing = taskRepository.getReferenceById(taskId);
        if(task != null && taskId == task.getId() && existing.getAuthor().getId() == userId) {

            existing.setPriority(task.getPriority());
            existing.setHeader(task.getHeader());
            existing.setStatus(task.getStatus());
            existing.setDescription(task.getDescription());
            return taskRepository.save(existing);
        }
        else {
            return null;
        }
    }

    /*public Task delete(long userId, Task task) {
        if(!task.isNew() & task.getAuthor().getId() == userId) {
            return taskRepository.delete(1);
        }
        else {
            return null;
        }
    }*/

    public Task changeStatus(long userId, long taskId, Status status) {
        Task referenceById = taskRepository.getReferenceById(taskId);
        referenceById.setStatus(status);
        return taskRepository.save(referenceById);

    }


    public Task setExecutors(long userId, long taskId, List<User> users) {
        Task referenceById = taskRepository.getReferenceById(taskId);
        referenceById.setExecutors(users);
        return taskRepository.save(referenceById);
    }
}
