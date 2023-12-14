package nikiforov.ruslan.taskmanagementsystem.services;

import nikiforov.ruslan.taskmanagementsystem.entity.User;
import nikiforov.ruslan.taskmanagementsystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {

        return userRepository.save(user);
    }
}
