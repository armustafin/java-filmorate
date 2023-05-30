package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Data
public class User {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final ZoneId DATA_ZONE = ZoneOffset.UTC;
    @EqualsAndHashCode.Exclude
    private int id;
    private String email;
    private String login;
    private String name;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthday;

}
