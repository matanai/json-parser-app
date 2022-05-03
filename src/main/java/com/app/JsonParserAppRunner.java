package com.app;

import com.app.service.UserJsonParserService;
import com.app.service.UserInteractionService;
import com.app.service.UserQueryService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class JsonParserAppRunner {

    public static final String CORRECT_FILE = "src/test/resources/file/input.json";

    private final UserInteractionService userInteraction = new UserInteractionService();
    private final UserQueryService userQueryRunner = new UserQueryService();
    private final UserJsonParserService userJsonParser = new UserJsonParserService();

    public static void main(String[] args) {
        new JsonParserAppRunner().run();
    }

    public void run() {
        var inputList = userJsonParser.extractUsers(CORRECT_FILE);

        try (var reader = new BufferedReader(new InputStreamReader(System.in))){
            var query = userInteraction.interact(reader);
            var resultList = userQueryRunner.runQuery(inputList, query);

            resultList.forEach(System.out::println);
            System.out.println("Found: " + resultList.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
