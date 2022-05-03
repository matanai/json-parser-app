package com.app.service;

import com.app.constant.Field;
import com.app.constant.Gender;
import com.app.exception.UserParserException;
import com.app.model.User;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Read, parse and extract user data from JSON file to a list of {@link User} objects
 * @version 1.0.1
 */
@Slf4j
public class UserJsonParserService {

    public static final String ERR_FIELD_TYPE_MISMATCH = "Warn: %s invalid in obj, skipping obj";
    public static final String ERR_FIELD_IS_NULL = "Warn: some of the fields were null, skipping obj";
    public static final String ERR_FILE_NOT_FOUND = "File %s does not exist";
    public static final String ERR_FILE_READ = "Error reading from file %s";

    /**
     * Attempt to read file containing JSON data and extract it to a list of {@link User}
     */
    public List<User> extractUsers(String fileName) {
        if (!Files.exists(Paths.get(fileName))) {
            throw new UserParserException(String.format(ERR_FILE_NOT_FOUND, fileName));
        }

        try (var reader = new FileReader(fileName)) {
            var jsonUsers = (JSONArray) new JSONParser().parse(reader);
            return buildUserList(jsonUsers);

        } catch (IOException | ParseException e) {
            throw new UserParserException(String.format(ERR_FILE_READ, fileName));
        }
    }

    /**
     * Iterate through provided {@link JSONArray} and map each extracted {@link JSONObject} to
     * {@link User}. If {@link JSONObject} contains corrupted data, type mismatch errors or
     * invalid field names, skip that element
     */
    private List<User> buildUserList(JSONArray jsonUsers) {
        List<User> userList = new ArrayList<>();

        for (Object obj : jsonUsers) {
            var jsonUser = (JSONObject) obj;
            var user = new User();

            try {
                user.setFirstName((String) convertValue(jsonUser, Field.FIRST_NAME));
                user.setLastName((String) convertValue(jsonUser, Field.LAST_NAME));
                user.setAge((Integer) convertValue(jsonUser, Field.AGE));
                user.setGender((Gender) convertValue(jsonUser, Field.GENDER));

                throwIfNullFields(user);
            } catch (RuntimeException e) {
                continue;
            }

            userList.add(user);
        }

        return userList;
    }

    /**
     * Convert data from {@link JSONObject} to an appropriate field of the {@link User} object using
     * provided {@link Field} enum. Throw {@link UserParserException} in case of type mismatch
     */
    private Object convertValue(JSONObject obj, Field fieldName) {
        try {
            var object = obj.get(fieldName.getFieldName());
            switch (fieldName) {
                case AGE:
                    return Integer.valueOf(object.toString());
                case GENDER:
                    return Gender.valueOf(object.toString().toUpperCase());
                default:
                    return object;
            }
        } catch (RuntimeException e) {
            log.error("{}", String.format(ERR_FIELD_TYPE_MISMATCH, fieldName));
            throw new UserParserException();
        }
    }

    /**
     * {@link User} object can not have null fields. Typically, occurs when field names in JSON
     * and {@link User} object do not match
     */
    private void throwIfNullFields(final User user) {
        if (Stream.of(
                user.getFirstName(),
                user.getLastName(),
                user.getAge(),
                user.getGender()
        ).anyMatch(Objects::isNull)) {
            log.error("{}", ERR_FIELD_IS_NULL);
            throw new UserParserException();
        }
    }
}
