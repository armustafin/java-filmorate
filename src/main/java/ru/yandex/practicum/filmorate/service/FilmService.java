package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InvalidFilmException;
import ru.yandex.practicum.filmorate.exception.InvalidUserException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.FilmStorage;
import ru.yandex.practicum.filmorate.repository.UserStorage;

import java.util.List;


@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(UserStorage userStorage, FilmStorage filmStorage) {
        this.userStorage = userStorage;
        this.filmStorage = filmStorage;
    }

    public Film add(Film film) {
        return filmStorage.save(film);
    }

    public Film put(Film film) {
        return filmStorage.update(film);
    }

    public List<Film> allFilms() {
        return filmStorage.findAllFilms();
    }

    public void addToFavorite(Film film, User user) {
        if (user == null) {
            throw new InvalidUserException("user not found");
        }
        if (film == null) {
            throw new InvalidFilmException("film not found");
        }
        filmStorage.addToFavorite(film, user);
    }

    public void deleteFromFavorite(Film film, User user) {
        if (user == null) {
            throw new InvalidUserException("user not found");
        }
        if (film == null) {
            throw new InvalidFilmException("film not found");
        }
        filmStorage.deleteFromFavorite(film, user);
    }

    public List<Film> allFavorite(Integer count) {
        if (count <= 0) {
            throw new InvalidFilmException("count less zero");
        }
        return filmStorage.allFavorite(count);
    }

    public Film getFilmById(int id) {
        if (filmStorage.findById(id) == null) {
            throw new InvalidFilmException("id not exist");
        }
        return filmStorage.findById(id);
    }

    public User getUserById(int id) {
        return userStorage.get(id);
    }
}
