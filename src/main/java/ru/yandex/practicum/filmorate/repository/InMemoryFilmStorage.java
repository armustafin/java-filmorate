package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.InvalidUserException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private static int seq;

    @Override
    public Film add(Film film) {
        film.setId(generateId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
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

    @Override
    public List<Film> allFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film delete(Integer id) {
       return films.remove(id);
    }

    @Override
    public Film get(Integer id) {
       return films.get(id);
    }

    @Override
    public void deleteAll() {
        films.clear();
    }
}
