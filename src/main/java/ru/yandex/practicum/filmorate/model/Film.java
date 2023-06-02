package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
public class Film {
    @EqualsAndHashCode.Exclude
    private int id;
    @NotBlank(message = "Please provide a name")
    @NotNull(message = "Please provide a name")
    private String name;
    private String description;
    @NotNull(message = "Please provide a release date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    @Positive(message = "Please provide a positive number")
    private int duration;
}
