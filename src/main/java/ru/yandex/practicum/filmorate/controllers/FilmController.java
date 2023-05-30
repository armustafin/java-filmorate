package ru.yandex.practicum.filmorate.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.InvalidFilmException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    final Map<Integer,Film> films = new HashMap<>();
    private static final int MAX_LENGTH_STRING = 200;
    private static final LocalDate MIN_DATA_RELEASE = LocalDate.of(1895, 12, 28);
    private static int seq;
    private static final String dateFormat = "yyyy-MM-dd";

    @GetMapping
    public List<Film> allFilms() {
        log.info("Текущее количество фильмов:" + films.size());
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        validateFilm(film);
        if (films.containsKey(film.getId())) {
            log.error("Такой фильм уже есть.");
            throw new InvalidFilmException("Такой фильм уже есть.");
        }
        films.put(film.getId(), film);
        log.info("Был добавлен фильм :" + film.getName());
        return film;
    }

    private static int generateId() {
        return ++seq;
    }

    @PutMapping
    public Film putFilm(@RequestBody Film film) {
        validateFilm(film);
        if (!films.containsKey(film.getId())) {
            log.error("Такого фильма нет.");
            throw new InvalidFilmException("Такого фильма нет.");
        }
        films.put(film.getId(),film);
        log.info("Были обновлены сведения о фильме: "+ film.getName());
        return film;
    }

    private static void validateFilm(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            log.error("Не правильно задано название фильма");
            throw new InvalidFilmException("Не правильно задано название фильма");
        }
        if (film.getDescription().length() > MAX_LENGTH_STRING) {
            log.error("слишком больше описание фильма: " + film.getName());
            throw new InvalidFilmException("У пользователя не задан логин.");
        }
        if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(MIN_DATA_RELEASE)) {
            log.error("Дата релиза раньше чем самая раняя дата: "+ film.getName());
            throw new InvalidFilmException("Дата релиза раньше чем самая раняя дата:");
        }
        if (film.getDuration() <= 0) {
            log.error("Продолжительность фильма должна быть положительной: " + film.getName());
            throw new InvalidFilmException("Продолжительность фильма должна быть положительной:");
        }
        if (film.getId() == 0) {
            film.setId(generateId());
        }
    }
}
