package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


class FilmControllerTest {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    FilmController filmController;

    Film film;

    @DisplayName("create film")
    @Test
    void shouldAddFilm() {
        filmController = new FilmController(new FilmService(new InMemoryUserStorage()));

        film = new Film();
        film.setId(1);
        film.setName("Ivan");
        film.setReleaseDate(LocalDate.parse("2010-03-12", FORMATTER));
        film.setDescription("as@ya.ru");
        film.setDuration(10);

        Film filmResult = filmController.create(film);

        List<Film> films = filmController.allFilms();
        Assertions.assertNotNull(films);
        Assertions.assertEquals(1, films.size(), 1, "users size");
        assertFilm(film, filmResult);
        film.setDuration(0);
        film.setId(2);
        try {
            filmResult = filmController.create(film);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        Assertions.assertNotNull(films.get(2));
        Assertions.assertEquals(1, films.size(), 1, "users size");
        assertFilm(film, filmResult);
        film.setName("");
        film.setDuration(120);
        try {
            filmResult = filmController.create(film);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Assertions.assertNotNull(films.get(2));
        Assertions.assertEquals(3, films.size(), "users size");
        assertFilm(film, filmResult);
        film.setDescription("s".repeat(201));

        try {
            filmResult = filmController.create(film);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Assertions.assertNotNull(films.get(2));
        Assertions.assertEquals(4, films.size(),"users size");
        assertFilm(film, filmResult);
        film.setReleaseDate(LocalDate.parse("1895-12-27", FORMATTER));
        film.setDescription("as@ya.ru");
        try {
            filmResult = filmController.create(film);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Assertions.assertNotNull(films.get(2));
        Assertions.assertEquals(5, films.size(), "users size");
        assertFilm(film, filmResult);
    }

    private void assertFilm(Film actual, Film film) {
        Assertions.assertEquals(film.getId(), actual.getId(), "film id");
        Assertions.assertEquals(film.getName(), actual.getName(), "film name");
        Assertions.assertEquals(film.getDuration(), actual.getDuration(), "film duration min");
        Assertions.assertEquals(film.getDescription(), actual.getDescription(), "film description");
        Assertions.assertEquals(film.getReleaseDate(), actual.getReleaseDate(), "film realise data");
    }
}