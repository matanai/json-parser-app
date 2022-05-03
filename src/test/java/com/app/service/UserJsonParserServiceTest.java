package com.app.service;

import com.app.constant.Gender;
import com.app.exception.UserParserException;
import com.app.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;

import static org.junit.jupiter.api.Assertions.*;

class UserJsonParserServiceTest {

    public static final String CORRECT_FILE = "src/test/resources/file/input.json";
    public static final String INVALID_FORMAT_FILE = "src/test/resources/file/invalid_format.json";
    public static final String INVALID_SCHEMA_FILE = "src/test/resources/file/invalid_schema.json";
    public static final String BAD_FILE_NAME = "bad_file_name";

    private final UserJsonParserService userJsonParserService = new UserJsonParserService();

    @Test
    void testExtractUsers_fileNotFound() {
        var msg = assertThrows(UserParserException.class,
                () -> userJsonParserService.extractUsers(BAD_FILE_NAME)).getMessage();

        assertEquals(String.format(UserJsonParserService.ERR_FILE_NOT_FOUND, BAD_FILE_NAME), msg);
    }

    @Test
    void testExtractUsers_invalidSchema() {
        var msg = assertThrows(UserParserException.class,
                () -> userJsonParserService.extractUsers(INVALID_FORMAT_FILE)).getMessage();

        assertEquals(String.format(UserJsonParserService.ERR_FILE_READ, INVALID_FORMAT_FILE), msg);
    }

    @Test
    void testExtractUsers_invalidFormat() {
        var resultList = userJsonParserService.extractUsers(INVALID_SCHEMA_FILE);

        assertEquals(7, resultList.size());

        assertTrue(resultList.stream().noneMatch(e -> e.getFirstName().equals("Scrooge")));
        assertTrue(resultList.stream().noneMatch(e -> e.getFirstName().equals("Gyro")));
        assertTrue(resultList.stream().noneMatch(e -> e.getFirstName().equals("Louie")));
        assertTrue(resultList.stream().noneMatch(e -> e.getFirstName().equals("Mickey")));
        assertTrue(resultList.stream().noneMatch(e -> e.getFirstName().equals("Goofy")));
    }

    @Test
    void testExtractUsers_success() {
        var resultDeque = new ArrayDeque<>(userJsonParserService.extractUsers(CORRECT_FILE));

        assertEquals(15, resultDeque.size());

        var userFirst = new User("Donald", "Duck", 86, Gender.MALE);
        assertEquals(userFirst, resultDeque.getFirst());

        var userLast = new User("Eega", "Beeva", 75, Gender.OTHER);
        assertEquals(userLast, resultDeque.getLast());
    }
}