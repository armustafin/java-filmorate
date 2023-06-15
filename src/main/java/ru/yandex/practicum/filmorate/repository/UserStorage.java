package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User add(User user);

    User put(User user);

    List<User> allUser();

    Boolean delete(Integer id);

    User get(Integer id);

    void deleteAll();
}
