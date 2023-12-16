package nikiforov.ruslan.taskmanagementsystem.data;

import nikiforov.ruslan.taskmanagementsystem.entity.Task;
import nikiforov.ruslan.taskmanagementsystem.entity.User;
import nikiforov.ruslan.taskmanagementsystem.model.Priority;
import nikiforov.ruslan.taskmanagementsystem.model.Status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserData {
    public static final User user1 = new User(101L, "ex6@mail.ru", "Sancho", "qwerty26", null);
    public static final User user2 = new User(102L, "ex9@mail.ru", "Alex", "qwerty29", null);
    public static final User user3 = new User(103L, "ex1@mail.ru", "Anna", "qwerty", null);

    public static final User user4 = new User(104L, "abc@mail.ru", "bob", "abc", null);


    public static final List<User> users = new ArrayList<>();

    static {
        Collections.addAll(users, user1, user2, user3);
    }

    public static User getNew() {
        return new User(null, "newTask", "456", "qwerty123", null);
    }

    public static User getExisting() {
        return new User(102L, "ex9@mail.ru", "Alex", "qwerty29", null);
    }
}
