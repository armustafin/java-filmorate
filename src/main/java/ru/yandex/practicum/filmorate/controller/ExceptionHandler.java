package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.InvalidFilmException;
import ru.yandex.practicum.filmorate.exception.InvalidUserException;
import ru.yandex.practicum.filmorate.model.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({InvalidFilmException.class, InvalidUserException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleIncorrectFilmException(final RuntimeException e ) {
        log.warn(e.getMessage(),e);
        if (e.getClass() == InvalidUserException.class) {
            return new ErrorResponse("User error: " + e.getMessage());
        } else {
            return new ErrorResponse("Film error: " + e.getMessage());
        }
    }
}
