package com.app.constant;

import com.app.service.UserInteractionService;
import lombok.experimental.UtilityClass;

/**
 * Set of predefined message used by {@link UserInteractionService} class
 * @version 1.0.1
 */
@UtilityClass
public class Message {

    public final String MSG_CHOOSE_SEARCH_FIELD = "Filter by field (firstName, lastName, age, gender). Empty input to skip:";
    public final String MSG_CHOOSE_OPERATOR = "Operator (=, >, <, >=, <=). Empty input to skip:";
    public final String MSG_CHOOSE_AGE = "Age:";
    public final String MSG_CHOOSE_VALUE = "Value:";
    public final String MSG_CHOOSE_GENDER = "Gender:";
    public final String MSG_CHOOSE_SORT_FIELD = "Sort by field (firstName, lastName, age, gender). Empty input to skip:";
    public final String MSG_CHOOSE_SORT_ASCENDING = "Ascending (y / n)?";
    public final String MSG_BAD_SEARCH_FIELD = "Field must be one of firstName, lastName, age or gender or empty input:";
    public final String MSG_BAD_OPERATOR = "Must specify operator (=, >, <, >=, <=) or empty:";
    public final String MSG_BAD_AGE = "Operand must be an integer:";
    public final String MSG_BAD_VALUE = "Must specify value:";
    public final String MSG_BAD_GENDER = "Must specify gender (male, female, other):";
    public final String MSG_BAD_SORT_FIELD = "Field must be one of firstName, lastName, age or gender or empty input:";
    public final String MSG_BAD_SORT_ASCENDING = "Sort must be ascending (y) or descending (n):";
}