package com.app.service;

import com.app.constant.Field;
import com.app.constant.Gender;
import com.app.constant.Message;
import com.app.constant.Operator;
import com.app.model.UserQuery;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Conduct user interactive session to collect user answers in order to build {@link UserQuery}
 * @version 1.0.1
 */
@Slf4j
public class UserInteractionService {

    private final UserQuery userQuery = new UserQuery();

    /**
     * Start interactive session
     * @throws IOException should the input stream fail
     */
    public UserQuery interact(BufferedReader reader) throws IOException {
        askSearchField(reader);

        if (userQuery.getSearchField() == Field.AGE) {
            askOperator(reader);
        }

        if (userQuery.getSearchField() != Field.DEFAULT) {
            if (userQuery.getSearchField() == Field.AGE) {
                askValueAge(reader);
            } else {
                if (userQuery.getSearchField() == Field.GENDER) {
                    askValueGender(reader);
                } else {
                    askValueName(reader);
                }
            }
        }

        askSortField(reader);

        if (userQuery.getSortField() != Field.DEFAULT) {
            askSortOrder(reader);
        }

        log.debug("*** User query: {}", userQuery);
        return userQuery;
    }

    private void askSearchField(BufferedReader reader) throws IOException {
        System.out.println(Message.MSG_CHOOSE_SEARCH_FIELD);

        Field searchField;

        while (true) {
            var userInput = reader.readLine();
            if ((searchField = Field.mapTo(userInput)) != null) {
                userQuery.setSearchField(searchField);
                break;
            }

            System.out.println(Message.MSG_BAD_SEARCH_FIELD);
        }

        log.debug("*** Search field set to: {}", userQuery.getSearchField());
    }

    private void askOperator(BufferedReader reader) throws IOException {
        System.out.println(Message.MSG_CHOOSE_OPERATOR);

        Operator operator;

        while (true) {
            var userInput = reader.readLine();
            if ((operator = Operator.mapTo(userInput)) != null) {
                userQuery.setOperator(operator);
                break;
            }

            System.out.println(Message.MSG_BAD_OPERATOR);
        }

        log.debug("*** Operator set to: {}", userQuery.getOperator());
    }

    private void askValueName(BufferedReader reader) throws IOException {
        System.out.println(Message.MSG_CHOOSE_VALUE);

        while (true) {
            var userInput = reader.readLine();
            if (userInput != null && userInput.length() > 0) {
                userQuery.setValue(userInput);
                break;
            }

            System.out.println(Message.MSG_BAD_VALUE);
        }

        log.debug("*** Value set to: {}", userQuery.getValue());
    }

    private void askValueAge(BufferedReader reader) throws IOException {
        System.out.println(Message.MSG_CHOOSE_AGE);

        while (true) {
            var userInput = reader.readLine();
            try {
                userQuery.setValue(Integer.parseInt(userInput));
                break;
            } catch (NumberFormatException ignore) {
                // NOP
            }

            System.out.println(Message.MSG_BAD_AGE);
        }

        log.debug("*** Value age set to: {}", userQuery.getValue());
    }

    private void askValueGender(BufferedReader reader) throws IOException {
        System.out.println(Message.MSG_CHOOSE_GENDER);

        while (true) {
            var userInput = reader.readLine();
            try {
                userQuery.setValue(Gender.valueOf(userInput.toUpperCase()));
                break;
            } catch (IllegalArgumentException ignore) {
                // NOP
            }

            System.out.println(Message.MSG_BAD_GENDER);
        }

        log.debug("*** Value gender set to: {}", userQuery.getValue());
    }

    private void askSortField(BufferedReader reader) throws IOException {
        System.out.println(Message.MSG_CHOOSE_SORT_FIELD);

        Field sortField;

        while (true) {
            var userInput = reader.readLine();
            if ((sortField = Field.mapTo(userInput)) != null) {
                userQuery.setSortField(sortField);
                break;
            }

            System.out.println(Message.MSG_BAD_SORT_FIELD);
        }

        log.debug("*** Sort field set to: {}", userQuery.getSortField());
    }


    private void askSortOrder(BufferedReader reader) throws IOException {
        System.out.println(Message.MSG_CHOOSE_SORT_ASCENDING);

        while (true) {
            var userInput = reader.readLine();
            if (userInput.equals("y")) {
                break;
            } else if (userInput.equals("n")) {
                userQuery.setSortAscending(false);
                break;
            } else {
                System.out.println(Message.MSG_BAD_SORT_ASCENDING);
            }
        }

        log.debug("*** Sort order set to ascending: {}", userQuery.isSortAscending());
    }
}
