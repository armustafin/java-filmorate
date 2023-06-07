package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.exception.InvalidUserException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilmRepository {
    private final Map<Integer, Film> films = new HashMap<>();
    private static int seq;

    public Film add(Film film) {
        film.setId(generateId());
        films.put(film.getId(), film);
        return film;
    }

    public Film put(Film film) {
        if (! films.containsKey(film.getId())) {
            throw new InvalidUserException("The film with с id " + film.getId() + " exist.");
        }
        films.put(film.getId(), film);
        return film;
    }

    private static int generateId() {
        return ++ seq;
    }

    public List<Film> allFilms() {
        return new ArrayList<>(films.values());
    }

    public Map<Integer, Film> getFilms() {
        return films;
    }
}
