package ru.yandex.practicum.filmorate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class RealiseDateValidator implements ConstraintValidator<RealiseDateContraint, LocalDate> {
    private static final LocalDate MIN_DATA_RELEASE = LocalDate.of(1895, 12, 28);
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.isAfter(MIN_DATA_RELEASE);
    }
}