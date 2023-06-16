package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Component
public interface UserStorage {
    User add(User user);

    User put(User user);

    List<User> allUser();

    User delete(Integer id);

    User get(Integer id);

    void deleteAll();
}
