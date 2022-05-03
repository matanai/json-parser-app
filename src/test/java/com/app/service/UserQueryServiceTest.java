package com.app.service;

import com.app.constant.Field;
import com.app.constant.Gender;
import com.app.constant.Operator;
import com.app.model.User;
import com.app.model.UserQuery;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserQueryServiceTest {

    private final UserQueryService userQueryService = new UserQueryService();

    @Test
    void testRunQuery_noFiltering_noSorting() {
        var resultDeque = new ArrayDeque<>(userQueryService.runQuery(getUserList(), new UserQuery()));

        assertEquals(7, resultDeque.size());

        var userFirst = resultDeque.getFirst();
        assertEquals("Mike", userFirst.getFirstName());
        assertEquals(23, userFirst.getAge());

        var userLast = resultDeque.getLast();
        assertEquals("HAL", userLast.getFirstName());
        assertEquals(9, userLast.getAge());
    }

    @Test
    void testRunQuery_noFiltering_sortByLastName_descending() {
        var query = new UserQuery();
        query.setSortField(Field.LAST_NAME);
        query.setSortAscending(false);

        var resultDeque = new ArrayDeque<>(userQueryService.runQuery(getUserList(), query));

        assertEquals(7, resultDeque.size());

        var userFirst = resultDeque.getFirst();
        assertEquals("Mike", userFirst.getFirstName());
        assertEquals(23, userFirst.getAge());

        var userLast = resultDeque.getLast();
        assertEquals("HAL", userLast.getFirstName());
        assertEquals(9, userLast.getAge());
    }

    @Test
    void testRunQuery_filterByFirstName_noSorting() {
        var query = new UserQuery();
        query.setSearchField(Field.FIRST_NAME);
        query.setValue("Mike");

        var resultDeque = new ArrayDeque<>(userQueryService.runQuery(getUserList(), query));

        assertEquals(2, resultDeque.size());

        var userFirst = resultDeque.getFirst();
        assertEquals("Mike", userFirst.getFirstName());
        assertEquals(23, userFirst.getAge());

        var userLast = resultDeque.getLast();
        assertEquals("Mike", userLast.getFirstName());
        assertEquals(41, userLast.getAge());
    }

    @Test
    void testRunQuery_filterByFirstName_sortByAge_ascending() {
        var query = new UserQuery();
        query.setSearchField(Field.FIRST_NAME);
        query.setValue("Mike");
        query.setSortField(Field.AGE);
        query.setSortAscending(false);

        var resultDeque = new ArrayDeque<>(userQueryService.runQuery(getUserList(), query));

        assertEquals(2, resultDeque.size());

        var userFirst = resultDeque.getFirst();
        assertEquals("Mike", userFirst.getFirstName());
        assertEquals(41, userFirst.getAge());

        var userLast = resultDeque.getLast();
        assertEquals("Mike", userLast.getFirstName());
        assertEquals(23, userLast.getAge());
    }

    @Test
    void testRunQuery_filterByGender_sortByLastName_ascending() {
        var query = new UserQuery();
        query.setSearchField(Field.GENDER);
        query.setValue(Gender.MALE);
        query.setSortField(Field.LAST_NAME);

        var resultDeque = new ArrayDeque<>(userQueryService.runQuery(getUserList(), query));

        assertEquals(4, resultDeque.size());

        var userFirst = resultDeque.getFirst();
        assertEquals("Doe", userFirst.getLastName());
        assertEquals(52, userFirst.getAge());

        var userLast = resultDeque.getLast();
        assertEquals("Smith", userLast.getLastName());
        assertEquals(23, userLast.getAge());
    }

    @Test
    void testRunQuery_filterByAge_defaultOperator_noSorting() {
        var query = new UserQuery();
        query.setSearchField(Field.AGE);
        query.setValue(52);

        var resultDeque = new ArrayDeque<>(userQueryService.runQuery(getUserList(), query));

        assertEquals(2, resultDeque.size());

        var userFirst = resultDeque.getFirst();
        assertEquals("Doe", userFirst.getLastName());
        assertEquals(52, userFirst.getAge());

        var userLast = resultDeque.getLast();
        assertEquals("Lee", userLast.getLastName());
        assertEquals(52, userFirst.getAge());
    }

    @Test
    void testRunQuery_filterByAge_lessThan_sortByLastName_ascending() {
        var query = new UserQuery();
        query.setSearchField(Field.AGE);
        query.setValue(35);
        query.setOperator(Operator.LESS_THAN);
        query.setSortField(Field.LAST_NAME);

        var resultDeque = new ArrayDeque<>(userQueryService.runQuery(getUserList(), query));

        assertEquals(4, resultDeque.size());

        var userFirst = resultDeque.getFirst();
        assertEquals("HAL", userFirst.getFirstName());
        assertEquals(9, userFirst.getAge());

        var userLast = resultDeque.getLast();
        assertEquals("Lisa", userLast.getFirstName());
        assertEquals(31, userLast.getAge());
    }

    private static List<User> getUserList() {
        return List.of(
            new User("Mike", "Smith", 23, Gender.MALE),
            new User("John", "Doe", 52, Gender.MALE),
            new User("Lisa", "Smith", 31, Gender.FEMALE),
            new User("Mike", "Gordon", 41, Gender.MALE),
            new User("Amy", "Adams", 18, Gender.FEMALE),
            new User("Robert", "Lee", 52, Gender.MALE),
            new User("HAL", "9000", 9, Gender.OTHER)
        );
    }
}