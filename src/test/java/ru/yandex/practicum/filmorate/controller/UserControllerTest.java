package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;

import java.util.Map;

import ru.yandex.practicum.filmorate.model.User;


class UserControllerTest {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    UserController userController;
    User user;

    @BeforeEach
    void setUp() {

    }

    @DisplayName("create user")
    @Test
    void shouldAddUser() {
        userController = new UserController();

        user = new User();
        user.setId(1);
        user.setName("Ivan");
        user.setBirthday(LocalDate.parse("2010-03-12", FORMATTER));
        user.setEmail("as@ya.ru");
        user.setLogin("as");

        User userResult = userController.create(user);

        Map<Integer, User> users = userController.users;
        Assertions.assertNotNull(users);
        Assertions.assertEquals(1, users.size(), 1, "users size");
        assertUser(user, userResult);
        user.setEmail("ya.ru");
        user.setId(2);
        try {
            userResult = userController.create(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        Assertions.assertNull(users.get(2));
        Assertions.assertEquals(1, users.size(), 1, "users size");
        assertUser(user, userResult);
        user.setLogin("");
        user.setEmail("as@ya.ru");
        try {
            userResult = userController.create(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Assertions.assertNull(users.get(2));
        Assertions.assertEquals(1, users.size(), 1, "users size");
        assertUser(user, userResult);
        user.setLogin("22as");
        user.setBirthday(LocalDate.parse("2023-05-31", FORMATTER));
        try {
            userResult = userController.create(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Assertions.assertNotNull(users.get(2));
        Assertions.assertEquals(1, users.size(), 1, "users size");
        assertUser(user, userResult);
        user.setBirthday(LocalDate.now());
        try {
            userResult = userController.create(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Assertions.assertNotNull(users.get(2));
        Assertions.assertEquals(2, users.size(), 1, "users size");
        assertUser(user, userResult);
    }

    private void assertUser(User actual, User user) {
        Assertions.assertEquals(user.getId(), actual.getId(), "user id");
        Assertions.assertEquals(user.getName(), actual.getName(), "user name");
        Assertions.assertEquals(user.getLogin(), actual.getLogin(), "user login");
        Assertions.assertEquals(user.getBirthday(), actual.getBirthday(), "user birthday");
    }

    @Test
    void putUser() {
    }

    @AfterEach
    void closeUp() {

    }
}