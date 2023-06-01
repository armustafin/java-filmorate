package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.InvalidFilmException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Validated
@Slf4j
@RequestMapping("/films")
public class FilmController {
    final Map<Integer,Film> films = new HashMap<>();
    private static final int MAX_LENGTH_STRING = 200;
    private static final LocalDate MIN_DATA_RELEASE = LocalDate.of(1895, 12, 28);
    private static int seq;

    @GetMapping
    public List<Film> allFilms() {
        log.info("Current number of films:" + films.size());
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        validateFilm(film);
        if (film.getId() == 0) {
            film.setId(generateId());
        }
        if (films.containsKey(film.getId())) {
            throw new InvalidFilmException("A current movie already exists.");
        }
        films.put(film.getId(), film);
        log.info("The movie has been added :" + film.getName());
        return film;
    }

    private static int generateId() {
        return ++seq;
    }

    @PutMapping
    public Film putFilm(@Valid @RequestBody Film film) {
        validateFilm(film);
        if (!films.containsKey(film.getId())) {
            throw new InvalidFilmException("There is no such movie");
        }
        films.put(film.getId(),film);
        log.info("A movie information has been updated: "+ film.getName());
        return film;
    }

    private static void validateFilm(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            throw new InvalidFilmException("The name of the movie is incorrect");
        }
        if (film.getDescription().length() > MAX_LENGTH_STRING) {
            throw new InvalidFilmException("too much the movie description");
        }
        if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(MIN_DATA_RELEASE)) {
           throw new InvalidFilmException("Release date earlier than earliest date: "+ film.getName());
        }
        if (film.getDuration() <= 0) {
            throw new InvalidFilmException("The movie duration must be positive:"+ film.getName());
        }
    }
}