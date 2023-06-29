package ru.yandex.practicum.filmorate.repository;


import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FilmStorage {
    Film save(Film film);

    Film update(Film film);

    List<Film> findAllFilms();

    Film delete(Integer id);

    Film findById(Integer id);

    void deleteAll();

    void addToFavorite(Film film, User user);

    void deleteFromFavorite(Film film, User user);

    List<Film> allFavorite(Integer count);

}
