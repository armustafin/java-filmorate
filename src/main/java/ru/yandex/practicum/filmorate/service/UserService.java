package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InvalidUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User add(User user) {
        checkNameUser(user);
        return userStorage.add(user);
    }

    public User put(User user) {
        checkNameUser(user);
        return userStorage.put(user);
    }

    public List<User> allUser() {
        return userStorage.allUser();
    }

    public void addToFriends(User user, User friend) {
        userStorage.addFriend(user, friend);
    }

    public void deletefromfriends(User user, User friend) {
        userStorage.deleteFriend(user, friend);
    }

    public List<User> AllFriends(User user) {
        return userStorage.AllFriends(user);
    }

    public UserStorage getUserStorage() {
        return userStorage;
    }

    public User getUserById(int id) {
        if (id <= 0) {
            throw new InvalidUserException("id user <= 0");
        }
        if (userStorage.get(id) == null) {
            throw new InvalidUserException("user not exist with current id");
        }
        return  userStorage.get(id);
    }

    public List<User> getFriendsById(Integer id) {
        return userStorage.AllFriends(userStorage.get(id));
    }

    public List<User> getCommonFriends(Integer id, Integer otherId) {
        Set<User> users = new HashSet<>(userStorage.AllFriends(userStorage.get(id)).stream()
                .collect(Collectors.toSet()));
        users.retainAll(new ArrayList<>(userStorage.AllFriends(userStorage.get(otherId))));
        return new ArrayList<>(users);
    }

    private static void checkNameUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
