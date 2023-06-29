package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Genre {
    @NotBlank(message = "Please provide a value")
    @NotNull(message = "Please provide a value")
    private String value;
    @EqualsAndHashCode.Exclude
    private int id;
    private String description;
}
