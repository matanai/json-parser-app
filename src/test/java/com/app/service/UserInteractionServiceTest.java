package com.app.service;

import com.app.constant.Field;
import com.app.constant.Gender;
import com.app.constant.Operator;
import com.app.model.UserQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserInteractionServiceTest {

    @InjectMocks
    private UserInteractionService userInteractionService;

    @Mock
    private BufferedReader mockReader;

    @Test
    void testInteract_noFiltering_noSorting() throws IOException {
        when(mockReader.readLine())
                .thenReturn("")
                .thenReturn("");

        var actual = userInteractionService.interact(mockReader);

        assertEquals(new UserQuery(), actual);
    }

    @Test
    void testInteract_filterByFirstName_sortByFirstName_ascending() throws IOException {
        when(mockReader.readLine())
                .thenReturn("firstName")
                .thenReturn("Mike")
                .thenReturn("firstName")
                .thenReturn("y");

        var expected = new UserQuery();
        expected.setSearchField(Field.FIRST_NAME);
        expected.setValue("Mike");
        expected.setSortField(Field.FIRST_NAME);

        var actual = userInteractionService.interact(mockReader);

        assertEquals(expected, actual);
    }

    @Test
    void testInteract_filterByAge_moreThan_sortByGender_descending() throws IOException {
        when(mockReader.readLine())
                .thenReturn("age")
                .thenReturn(">")
                .thenReturn("23")
                .thenReturn("gender")
                .thenReturn("n");

        var expected = new UserQuery();
        expected.setSearchField(Field.AGE);
        expected.setValue(23);
        expected.setSortField(Field.GENDER);
        expected.setOperator(Operator.MORE_THAN);
        expected.setSortAscending(false);

        var actual = userInteractionService.interact(mockReader);

        assertEquals(expected, actual);
    }

    @Test
    void testInteract_filterByAge_defaultOperator_sortByGender_descending() throws IOException {
        when(mockReader.readLine())
                .thenReturn("age")
                .thenReturn("")
                .thenReturn("23")
                .thenReturn("gender")
                .thenReturn("n");

        var expected = new UserQuery();
        expected.setSearchField(Field.AGE);
        expected.setValue(23);
        expected.setSortField(Field.GENDER);
        expected.setSortAscending(false);

        var actual = userInteractionService.interact(mockReader);

        assertEquals(expected, actual);
    }

    @Test
    void testInteract_noFiltering_sortByAge_ascending() throws IOException {
        when(mockReader.readLine())
                .thenReturn("")
                .thenReturn("age")
                .thenReturn("y");

        var expected = new UserQuery();
        expected.setSortField(Field.AGE);

        var actual = userInteractionService.interact(mockReader);

        assertEquals(expected, actual);
    }

    @Test
    void testInteract_filterByGender_sortByLastName_ascending() throws IOException {
        when(mockReader.readLine())
                .thenReturn("gender")
                .thenReturn("male")
                .thenReturn("lastName")
                .thenReturn("y");

        var expected = new UserQuery();
        expected.setSearchField(Field.GENDER);
        expected.setValue(Gender.MALE);
        expected.setSortField(Field.LAST_NAME);

        var actual = userInteractionService.interact(mockReader);

        assertEquals(expected, actual);
    }
}