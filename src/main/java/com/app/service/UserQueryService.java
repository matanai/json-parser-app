package com.app.service;

import com.app.constant.Field;
import com.app.constant.Gender;
import com.app.constant.Operator;
import com.app.model.User;
import com.app.model.UserQuery;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Build and run custom queries, using comparators and predicates based on {@link User} object
 * @version 1.0.1
 */
public class UserQueryService {

    public static final String ERR_INVALID_TYPE = "Value must be of type '%s'";

    /**
     * Append filters and sorting using the provided {@link UserQuery} object, run query and
     * collect results to a list of {@link User}
     */
    public List<User> runQuery(List<User> userList, UserQuery query) {
        return userList.stream()
                .filter(getFilter(
                        query.getSearchField(),
                        query.getOperator(),
                        query.getValue())
                ).sorted(getSorter(
                        query.getSortField(),
                        query.isSortAscending())
                ).collect(Collectors.toList());
    }

    /**
     * Each method performs explicit type check using {@link Class#getClass()} in order to avoid errors
     */
    private Predicate<User> getFilter(Field searchField, Operator operator, final Object value) {
        switch (searchField) {
            case FIRST_NAME:
                return getFirstNameFilter(value);
            case LAST_NAME:
                return getLastNameFilter(value);
            case GENDER:
                return getGenderFilter(value);
            case AGE:
                return getAgeFilter(value, operator);
            default:
                return e -> true;
        }
    }

    private Predicate<User> getFirstNameFilter(final Object value) {
        if (value.getClass() != String.class) {
            throw new IllegalArgumentException(
                    String.format(ERR_INVALID_TYPE, String.class.getSimpleName()));
        }

        return user -> user.getFirstName().equals(value);
    }

    private Predicate<User> getLastNameFilter(final Object value) {
        if (value.getClass() != String.class) {
            throw new IllegalArgumentException(
                    String.format(ERR_INVALID_TYPE, String.class.getSimpleName()));
        }

        return user -> user.getLastName().equals(value);
    }

    private Predicate<User> getGenderFilter(final Object value) {
        if (value.getClass() != Gender.class) {
            throw new IllegalArgumentException(
                    String.format(ERR_INVALID_TYPE, Gender.class.getSimpleName()));
        }

        return user -> user.getGender() == value;
    }

    /**
     * If user did not specify operator for age filter, use {@link Operator#EQUAL}
     */
    private Predicate<User> getAgeFilter(final Object value, Operator operator) {
        if (value.getClass() != Integer.class) {
            throw new IllegalArgumentException(
                    String.format(ERR_INVALID_TYPE, Integer.class.getSimpleName()));
        }

        switch (operator) {
            case MORE_THAN:
                return user -> user.getAge() > (Integer) value;
            case MORE_THAN_EQUAL:
                return user -> user.getAge() >= (Integer) value;
            case LESS_THAN:
                return user -> user.getAge() < (Integer) value;
            case LESS_THAN_EQUAL:
                return user -> user.getAge() <= (Integer) value;
            default:
                return user -> Objects.equals(user.getAge(), value);
        }
    }

    /**
     * Default comparator always returns 0, hence if user left sorting question blank,
     * the final collection will not be sorted
     */
    private Comparator<User> getSorter(Field sortField, boolean sortAscending) {
        Comparator<User> comparator;

        switch (sortField) {
            case FIRST_NAME:
                comparator = Comparator.comparing(User::getFirstName);
                break;
            case LAST_NAME:
                comparator = Comparator.comparing(User::getLastName);
                break;
            case GENDER:
                comparator = Comparator.comparing(User::getGender);
                break;
            case AGE:
                comparator = Comparator.comparing(User::getAge);
                break;
            default:
                comparator = (u1, u2) -> 0;
        }

        if (!sortAscending) {
            return comparator.reversed();
        }

        return comparator;
    }
}
