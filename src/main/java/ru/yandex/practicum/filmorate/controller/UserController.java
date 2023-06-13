package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.InvalidUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@Validated
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Integer id) {
        if (id == null || id <= 0) {
            throw new InvalidUserException("id or friendId eqals null or <= 0");
        }
        if (userService.getUserStorage().get(id) == null) {
            throw new InvalidUserException("id not exist");
        }
        log.info("get user by id " + id);
        return userService.getUserStorage().get(id);
    }

    @GetMapping
    public List<User> allUsers() {
        List<User> users = userService.allUser();
        log.info("Current number of users: " + users.size());
        return users;
    }

    @GetMapping("/{id}/friends")
    public List<User> allFriends(@PathVariable("id") Integer id) {
        if (id == null || id <= 0) {
            throw new InvalidUserException("id or friendId eqals null or <= 0");
        }
        if (userService.getUserStorage().get(id) == null) {
            throw new InvalidUserException("id not exist");
        }
        List<User> users = new ArrayList<>(userService.getUserStorage().get(id).getFriends()).stream()
                .map(key -> userService.getUserStorage().get(key)).collect(Collectors.toList());
        log.info("Current number friends of user: " + users.size());
        return users;
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> commonFriends(@PathVariable("id") Integer id, @PathVariable("otherId") Integer otherId) {
        if (id == null || otherId == null || id <= 0 || otherId <= 0) {
            throw new InvalidUserException("id or friendId eqals null or <= 0");
        }
        if (userService.getUserStorage().get(id) == null || userService.getUserStorage().get(otherId) == null) {
            throw new InvalidUserException("id or otherId not exist");
        }
        Set<User> users = new HashSet<>(userService.getUserStorage().get(id).getFriends()).stream()
                .map(key -> userService.getUserStorage().get(key)).collect(Collectors.toSet());
        users.retainAll(new ArrayList<>(userService.getUserStorage().get(otherId).getFriends()).stream()
                .map(key -> userService.getUserStorage().get(key)).collect(Collectors.toList()));

        log.info("Current number friends of user: " + users.size());
        return new ArrayList<>(users);
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        checkNameUser(user);
        log.info("User has been created. His login: " + user.getLogin());
        return userService.add(user);
    }

    @PutMapping
    public User putUser(@Valid @RequestBody User user) {
        checkNameUser(user);
        log.info("User has been updated. His login:" + user.getLogin());
        return userService.put(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public Boolean addToFriend(@PathVariable("id") Integer id, @PathVariable("friendId") Integer friendId) {
        if (id == null || friendId == null || id <= 0 || friendId <= 0) {
            throw new InvalidUserException("id or friendId eqals null or <= 0");
        }
        if (userService.getUserStorage().get(id) == null || userService.getUserStorage().get(friendId) == null) {
            throw new InvalidUserException("id or otherId not exist");
        }
        log.info("User add to friend");
        return userService.addToFriends(userService.getUserStorage().get(id),
                userService.getUserStorage().get(friendId));
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public Boolean delFromFriends(@PathVariable("id") Integer id, @PathVariable("friendId") Integer friendId) {
        if (id == null || friendId == null || id <= 0 || friendId <= 0) {
            throw new InvalidUserException("id or friendId eqals null or <= 0");
        }
        if (userService.getUserStorage().get(id) == null || userService.getUserStorage().get(friendId) == null) {
            throw new InvalidUserException("id or otherId not exist");
        }
        return userService.delFromFriends(userService.getUserStorage().get(id),
                userService.getUserStorage().get(friendId));
    }

    private static void checkNameUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
