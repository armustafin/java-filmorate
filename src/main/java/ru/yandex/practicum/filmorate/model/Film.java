package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Data
@Configuration
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
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final ZoneId DATA_ZONE = ZoneOffset.UTC;
}
