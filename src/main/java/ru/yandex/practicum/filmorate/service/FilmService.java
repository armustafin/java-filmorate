package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InvalidFilmException;
import ru.yandex.practicum.filmorate.exception.InvalidUserException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.FilmStorage;
import ru.yandex.practicum.filmorate.repository.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.repository.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage = new InMemoryFilmStorage();
    private final UserStorage userStorage;

    @Autowired
    public FilmService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Film add(Film film) {
        return filmStorage.add(film);
    }

    public Film put(Film film) {
        return filmStorage.put(film);
    }

    public List<Film> allFilms() {
        return filmStorage.allFilms();
    }

    public Boolean addToFavorite(Film film, User user) {
        if (film != null && user != null && filmStorage.allFilms().contains(film) &&
                userStorage.allUser().contains(user)) {
            filmStorage.get(film.getId()).getFavorite().add(user.getId());
            return true;
        }
        return false;
    }

    public Boolean deleteFromFavorite(Film film, User user) {
        if (user == null) {
            throw new InvalidUserException("user not found");
        }
        if (film == null) {
            throw new InvalidFilmException("film not found");
        }
        if (filmStorage.allFilms().contains(film) &&
                userStorage.allUser().contains(user)) {
            if (film.getFavorite().contains(user.getId())) {
                filmStorage.get(film.getId()).getFavorite().remove(user.getId());
            }
            return true;
        }
        return false;
    }

    public List<Film> allFavorite(Integer count) {
        if (count <= 0) {
            throw new InvalidFilmException("count less zero");
        }
        return filmStorage.allFilms().stream()
                .sorted((p0, p1) -> {return Integer.compare(p1.getFavorite().size(), p0.getFavorite().size());})
                .limit(count)
                .collect(Collectors.toList());
    }

    public Film getFilmById(int id) {
        if (filmStorage.get(id) == null) {
            throw new InvalidFilmException("id not exist");
        }
        return filmStorage.get(id);
    }

    public User getUserById(int id) {
        return userStorage.get(id);
    }
}
