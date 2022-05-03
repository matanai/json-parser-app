package com.app.constant;

import java.util.Arrays;

/**
 * Represents fields of the {@link com.app.model.User} class
 * @version 1.0.1
 */
public enum Field {

    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    AGE("age"),
    GENDER("gender"),
    DEFAULT("");

    private final String fieldName;

    Field(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static Field mapTo(String userInput) {
        return Arrays.stream(Field.values())
                .filter(field -> field.fieldName.equals(userInput))
                .findAny()
                .orElse(null);
    }
}
