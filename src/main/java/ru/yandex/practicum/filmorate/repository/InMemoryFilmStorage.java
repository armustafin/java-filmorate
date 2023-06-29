package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.InvalidUserException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private final Map<Integer, Set<Integer>> filmInFavorite = new HashMap<>();
    private static int seq;

    @Override
    public Film save(Film film) {
        film.setId(generateId());
        films.put(film.getId(), film);
        filmInFavorite.put(film.getId(), new HashSet<>());
        return film;
    }

    @Override
    public void addToFavorite(Film film, User user) {
        Set<Integer> usersIds = filmInFavorite.computeIfAbsent(film.getId(), id -> new HashSet<>());
        usersIds.add(user.getId());
    }

    @Override
    public void deleteFromFavorite(Film film, User user) {
        Set<Integer> usersIds = filmInFavorite.computeIfAbsent(film.getId(), id -> new HashSet<>());
        usersIds.remove(user.getId());
    }

    @Override
    public List<Film> allFavorite(Integer count) {
        return findAllFilms().stream()
                .sorted((p0, p1) -> {return Integer.compare(filmInFavorite.get(p1.getId()).size(),
                        filmInFavorite.get(p0.getId()).size());})
                .limit(count)
                .collect(Collectors.toList());
    }
    @Override
    public Film update(Film film) {
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
    public List<Film> findAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film delete(Integer id) {
       return films.remove(id);
    }

    @Override
    public Film findById(Integer id) {
       return films.get(id);
    }

    @Override
    public void deleteAll() {
        films.clear();
    }
}
