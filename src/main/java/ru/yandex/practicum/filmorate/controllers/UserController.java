package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.InvalidUserException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    final Map<Integer, User> users = new HashMap<>();
    private static int seq;
    @GetMapping
    public List<User> allUsers() {
        log.info("Текущее количество пользователей:" + users.size());
        return new ArrayList<>(users.values());
    }

    private static int generateId() {
        return ++seq;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        validateUsers(user);
        if (users.containsKey(user.getId())) {
            log.error("Пользователь с id " +
                    user.getId() + " уже зарегистрирован.");
            throw new InvalidUserException("Пользователь с id " +
                    user.getId() + " уже зарегистрирован.");
        }

        users.put(user.getId(),user);
        log.info("Был создан пользователь: " + user.getLogin());
        return user;
    }
    @PutMapping
    public User putUser(@RequestBody User user) {
        validateUsers(user);
        if (!users.containsKey(user.getId())) {
            log.error("Такого пользователя нет");
            throw new InvalidUserException("Такого пользователя нет");
        }
        users.put(user.getId(), user);
        log.info("Был обновлен пользователь:" + user.getLogin());
        return user;
    }

    private static void validateUsers(User user) {
        String email = user.getEmail();
        if (email == null || email.isBlank() || ! email.contains("@")) {
            log.error("Не правильно задан email у пользователя: " + user.getLogin());
            throw new InvalidUserException("Не правильно задан email");
        }
        if (user.getLogin().isBlank()) {
            log.error("У пользователя не задан логин " + user.getEmail());
            throw new InvalidUserException("У пользователя не задан логин.");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("У пользователя дата рождения не может быть в будущем: "+ user.getBirthday());
            throw new InvalidUserException("У пользователя дата рождения не может быть в будущем.");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getId() == 0) {
            user.setId(generateId());
        }
    }
}
