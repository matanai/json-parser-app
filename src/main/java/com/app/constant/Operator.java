package com.app.constant;

import java.util.Arrays;

/**
 * Represents comparison operators used when filtering data by {@link Field#AGE}
 * @version 1.0.1
 */
public enum Operator {

    EQUAL("="),
    LESS_THAN("<"),
    MORE_THAN(">"),
    LESS_THAN_EQUAL("<="),
    MORE_THAN_EQUAL(">="),
    DEFAULT("");

    private final String operatorName;

    Operator(String operatorName) {
        this.operatorName = operatorName;
    }

    public static Operator mapTo(String userInput) {
        return Arrays.stream(Operator.values())
                .filter(operator -> operator.operatorName.equals(userInput))
                .findAny()
                .orElse(null);
    }
}
