package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.exception.InvalidUserException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {
    private final Map<Integer, User> users = new HashMap<>();
    private static int seq;

    public UserRepository() {
    }

    public User add(User user) {
        user.setId(generateId());
        users.put(user.getId(), user);
        return user;
    }

    public User put(User user) {
        if (! users.containsKey(user.getId())) {
            throw new InvalidUserException("The user with с id " + user.getId() + " exist.");
        }
        users.put(user.getId(), user);
        return user;
    }

    private static int generateId() {
        return ++ seq;
    }

    public List<User> allUsers() {
        return new ArrayList<>(users.values());
    }

    public Map<Integer, User> getUsers() {
        return users;
    }
}
