package nikiforov.ruslan.taskmanagementsystem.controllers;

import nikiforov.ruslan.taskmanagementsystem.entity.User;
import nikiforov.ruslan.taskmanagementsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/create")
    public ResponseEntity<User> create(@RequestBody User user) {
        User newUser = userService.create(user);
        if(newUser != null) {
            return ResponseEntity.ok(newUser);
        }
        else {
            return ResponseEntity.status(402).build();
        }
    }
}
