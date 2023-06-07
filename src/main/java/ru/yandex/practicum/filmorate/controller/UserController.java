package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@RestController
@Validated
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository = new UserRepository();

    @GetMapping
    public List<User> allUsers() {
        List<User> users = userRepository.allUsers();
        log.info("Current number of users: " + users.size());
        return users;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        checkNameUser(user);
        log.info("User has been created. His login: " + user.getLogin());
        return userRepository.add(user);
    }

    @PutMapping
    public User putUser(@Valid @RequestBody User user) {
        checkNameUser(user);
        log.info("User has been updated. His login:" + user.getLogin());
        return userRepository.put(user);
    }

     private static void checkNameUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    public Map<Integer, User> getUsers() {
        return userRepository.getUsers();
    }
}
