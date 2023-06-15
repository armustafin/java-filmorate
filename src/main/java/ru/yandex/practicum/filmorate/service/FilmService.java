package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InvalidFilmException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.FilmStorage;
import ru.yandex.practicum.filmorate.repository.InMemoryFilmStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage = new InMemoryFilmStorage();
    private final UserService userService;

    @Autowired
    public FilmService(UserService userService) {
        this.userService = userService;
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
                userService.allUser().contains(user)) {
            filmStorage.get(film.getId()).getFavorite().add(user.getId());
            return true;
        }
        return false;
    }

    public Boolean delFromFavorite(Film film, User user) {
        if (film != null && user != null && filmStorage.allFilms().contains(film) &&
                userService.allUser().contains(user)) {
            if (film.getFavorite().contains(user.getId())) {
                filmStorage.get(film.getId()).getFavorite().remove(user.getId());
            }
            return true;
        }
        return false;
    }

    public List<Film> AllFavorite(Integer count) {
        if (count == null || count <= 0) {
            throw new InvalidFilmException("count not exist");
        }
        return filmStorage.allFilms().stream().sorted((p0, p1) -> {
            return Integer.compare(p1.getFavorite().size(), p0.getFavorite().size());
        }).limit(count).collect(Collectors.toList());
    }

    public Film getFilmById(int id) {
        if (id <= 0) {
            throw new InvalidFilmException("id eqals null or <= 0");
        }
        if (filmStorage.get(id) == null) {
            throw new InvalidFilmException("id not exist");
        }
        return filmStorage.get(id);
    }

    public User getUserById(int id) {
        return userService.getUserById(id);
    }

    public UserService getUserService() {
        return userService;
    }

    public FilmStorage getFilmStorage() {
        return filmStorage;
    }
}
