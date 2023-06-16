package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.InvalidUserException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private static int seq;

    @Override
    public User add(User user) {
        user.setId(generateId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User put(User user) {
        if (! users.containsKey(user.getId())) {
            throw new InvalidUserException("The user with с id " + user.getId() + " exist.");
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User delete(Integer id) {
        return users.remove(id);
    }

    @Override
    public User get(Integer id) {
       return users.get(id);
    }

    @Override
    public void deleteAll() {
        users.clear();
    }

    private static int generateId() {
        return ++ seq;
    }

    @Override
    public List<User> allUser() {
        return new ArrayList<>(users.values());
    }

}
