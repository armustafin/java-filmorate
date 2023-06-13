package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.InvalidFilmException;
import ru.yandex.practicum.filmorate.exception.InvalidUserException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable("id") Integer id) {
        if (id == null || id <= 0) {
            throw new InvalidFilmException("id eqals null or <= 0");
        }
        if (filmService.getFilmStorage().get(id) == null) {
            throw new InvalidFilmException("id not exist");
        }
        log.info("get film by id " + id);
        return filmService.getFilmStorage().get(id);
    }

    @GetMapping
    public List<Film> allFilms() {
        List<Film> films = filmService.allFilms();
        log.info("Current number of films:" + films.size());
        return films;
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("The movie has been added :" + film.getName());
        return filmService.add(film);
    }

    @PutMapping
    public Film putFilm(@Valid @RequestBody Film film) {
        log.info("A movie information has been updated: " + film.getName());
        return filmService.put(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public Boolean addLike(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        if (id == null || id <= 0) {
            throw new InvalidFilmException("id film eqals null or <= 0");
        }
        if (userId == null || userId <= 0) {
            throw new InvalidUserException("id user null or <= 0");
        }
        if (filmService.getFilmStorage().get(id) == null) {
            throw new InvalidFilmException("film not exist with current id");
        }
        if (filmService.getUserService().getUserStorage().get(userId) == null) {
            throw new InvalidUserException("user not exist with current id");
        }
        log.info("A movie add to favorite");
        return filmService.addToFavorite(filmService.getFilmStorage().get(id),
                filmService.getUserService().getUserStorage().get(userId));
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Boolean deleteLike(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        if (id == null || id <= 0) {
            throw new InvalidFilmException("id film eqals null or <= 0");
        }
        if (userId == null || userId <= 0) {
            throw new InvalidUserException("id user null or <= 0");
        }
        if (filmService.getFilmStorage().get(id) == null) {
            throw new InvalidFilmException("film not exist with current id");
        }
        if (filmService.getUserService().getUserStorage().get(userId) == null) {
            throw new InvalidUserException("user not exist with current id");
        }
        log.info("A movie delete to favorite");
        return filmService.delFromFavorite(filmService.getFilmStorage().get(id),
                filmService.getUserService().getUserStorage().get(userId));
    }

    @GetMapping("/popular")
    public List<Film> findFavoriteFilms(@RequestParam(defaultValue = "10", required = false) Integer count) {
        if (count == null || count <= 0) {
            throw new InvalidFilmException("count not exist");
        }
        log.debug("list sort favorite");
        return filmService.AllFavorite(count);
    }
}
