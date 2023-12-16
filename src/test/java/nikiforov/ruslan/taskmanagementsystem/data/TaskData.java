package nikiforov.ruslan.taskmanagementsystem.data;

import nikiforov.ruslan.taskmanagementsystem.entity.Task;
import nikiforov.ruslan.taskmanagementsystem.entity.User;
import nikiforov.ruslan.taskmanagementsystem.model.Priority;
import nikiforov.ruslan.taskmanagementsystem.model.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskData {

    public static final Task task1 = new Task(101L, "header1", "dscr1", Status.WAITING, Priority.MEDIUM,
            UserData.user1, List.of(UserData.user2), null);
    public static final Task task2 = new Task(102L, "header2", "dscr2", Status.IN_PROGRESS, Priority.HIGH,
            UserData.user1, List.of(UserData.user2, UserData.user3), null);
    public static final Task task3 = new Task(103L, "header3", "dscr3", Status.WAITING, Priority.MEDIUM,
            UserData.user2, List.of(UserData.user4), null);
    public static final Task task4 = new Task(104L, "header4", "dscr4", Status.FINISHED, Priority.LOW,
            UserData.user2, List.of(UserData.user1), null);

    public static final Task task5 = new Task(105L, "header5", "dscr5", Status.FINISHED, Priority.LOW,
            UserData.user4, List.of(UserData.user4), null);

    public static final List<Task> tasks = new ArrayList<>();

    static {
        Collections.addAll(tasks, task1, task2, task3, task4, task5);
    }

    public static Task getNew() {
        return new Task(null, "newTask", "someDescr", Status.FINISHED, Priority.LOW,
                null, null, null);
    }

    public static Task getExisting() {
        return task5;
    }


}
