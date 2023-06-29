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
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import javax.validation.Valid;
import java.util.List;


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
        log.info("get user by id " + id);
        return userService.getUserById(id);
    }

    @GetMapping
    public List<User> allUsers() {
        List<User> users = userService.allUser();
        log.info("Current number of users: " + users.size());
        return users;
    }

    @GetMapping("/{id}/friends")
    public List<User> allFriends(@PathVariable("id") Integer id) {
        List<User> users = userService.getFriendsById(id);
        log.info("Current number friends of user: " + users.size());
        return users;
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> commonFriends(@PathVariable("id") Integer id, @PathVariable("otherId") Integer otherId) {
        List<User> users = userService.getCommonFriends(id, otherId);
        log.info("Current number friends of user: " + users.size());
        return users;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("User has been created. His login: " + user.getLogin());
        return userService.add(user);
    }

    @PutMapping
    public User putUser(@Valid @RequestBody User user) {
        log.info("User has been updated. His login:" + user.getLogin());
        return userService.put(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addToFriend(@PathVariable("id") Integer id, @PathVariable("friendId") Integer friendId) {
        log.info("User add to friend");
        userService.addToFriends(userService.getUserById(id),
                userService.getUserById(friendId));
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFromFriends(@PathVariable("id") Integer id, @PathVariable("friendId") Integer friendId) {
        userService.deletefromfriends(userService.getUserById(id),
                userService.getUserById(friendId));
    }
}
