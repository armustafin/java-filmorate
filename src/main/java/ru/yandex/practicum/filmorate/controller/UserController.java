package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.InvalidUserException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@Slf4j
@RequestMapping("/users")
public class UserController {
    final Map<Integer, User> users = new HashMap<>();
    private static int seq;
    @GetMapping
    public List<User> allUsers() {
        log.info("Current number of users: " + users.size());
        return new ArrayList<>(users.values());
    }

    private static int generateId() {
        return ++seq;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        validateUsers(user);
        if (user.getId() == 0) {
            user.setId(generateId());
        }
        if (users.containsKey(user.getId())) {
           throw new InvalidUserException("The user with с id " +
                    user.getId() + " exist.");
        }
        users.put(user.getId(),user);
        log.info("User has been created. His login: " + user.getLogin());
        return user;
    }

    @PutMapping
    public User putUser(@Valid @RequestBody User user) {
        validateUsers(user);
        if (!users.containsKey(user.getId())) {
           throw new InvalidUserException("There is no user");
        }
        users.put(user.getId(), user);
        log.info("User has been updated. His login:" + user.getLogin());
        return user;
    }

    private static void validateUsers(User user) {
        String email = user.getEmail();
        if (email == null || email.isBlank() || ! email.contains("@")) {
            throw new InvalidUserException("The user's email address is incorrect: " + user.getLogin());
        }
        if (user.getLogin().isBlank()) {
           throw new InvalidUserException("The user has no login");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new InvalidUserException("The user's date of birth cannot be in the future: " + user.getBirthday());
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
