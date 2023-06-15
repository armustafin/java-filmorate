package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InvalidUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.repository.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage = new InMemoryUserStorage();

    public User add(User user) {
        return userStorage.add(user);
    }

    public User put(User user) {
        return userStorage.put(user);
    }

    public List<User> allUser() {
        return userStorage.allUser();
    }

    public Boolean addToFriends(User user, User friend) {
        if (user != null && friend != null && userStorage.allUser().contains(user) &&
                userStorage.allUser().contains(friend)) {
            user.getFriends().add(friend.getId());
            friend.getFriends().add(user.getId());
            return true;
        }
        return false;
    }

    public Boolean delFromFriends(User user, User friend) {
        if (user != null && friend != null && userStorage.allUser().contains(user) &&
                userStorage.allUser().contains(friend)) {
            if (user.getFriends().contains(friend.getId())) {
                user.getFriends().remove(friend.getId());
                friend.getFriends().remove(user.getId());
            }
            return true;
        }
        return false;
    }

    public List<User> AllFriends(User user) {
        if (user != null && userStorage.allUser().contains(user)) {
            return user.getFriends().stream().map(key -> userStorage.get(key)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public UserStorage getUserStorage() {
        return userStorage;
    }

    public User getUserById(int id) {
        if (id <= 0) {
            throw new InvalidUserException("id user null or <= 0");
        }
        if (userStorage.get(id) == null) {
            throw new InvalidUserException("user not exist with current id");
        }
        return  userStorage.get(id);
    }

    public List<User> getFriendsById(Integer id) {
        return new ArrayList<>(getUserById(id).getFriends()).stream()
                .map(key -> getUserById(key)).collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Integer id, Integer otherId) {
        Set<User> users = new HashSet<>(getUserStorage().get(id).getFriends()).stream()
                .map(key -> getUserStorage().get(key)).collect(Collectors.toSet());
        users.retainAll(new ArrayList<>(getUserStorage().get(otherId).getFriends()).stream()
                .map(key -> getUserStorage().get(key)).collect(Collectors.toList()));
        return new ArrayList<>(users);
    }
}
