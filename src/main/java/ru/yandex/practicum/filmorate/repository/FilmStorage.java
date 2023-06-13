package ru.yandex.practicum.filmorate.repository;


import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage
{
    Film add(Film film);

    Film put(Film film);

    List<Film> allFilms();

    Boolean del(Integer id);

    Film get(Integer id);

    void deleteAll();
}
