package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final FilmRepository filmRepository = new FilmRepository();
    private static final LocalDate MIN_DATA_RELEASE = LocalDate.of(1895, 12, 28);

    @GetMapping
    public List<Film> allFilms() {
        List<Film> films = filmRepository.allFilms();
        log.info("Current number of films:" + films.size());
        return films;
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("The movie has been added :" + film.getName());
        return filmRepository.add(film);
    }

    @PutMapping
    public Film putFilm(@Valid @RequestBody Film film) {
        log.info("A movie information has been updated: "+ film.getName());
        return   filmRepository.put(film);
    }

   public Map<Integer, Film> getFilms() {
        return filmRepository.getFilms();
    }
}
