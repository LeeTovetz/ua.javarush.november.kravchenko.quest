package ua.javarush.quest.logic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ua.javarush.quest.model.AdventureAnswer;
import ua.javarush.quest.model.Player;
import ua.javarush.quest.exception.AdventureNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestServiceTest {
    AdventureLogic questService = new AdventureLogic();
    private String questName;

    public QuestServiceTest() throws IOException {
        questName = questService.addAdventureAndReturnName("quest.json");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void checkGetQuestionTextById(int id) {
        String expectedText = "";
        String text = questService.getTaskTextById(questName, id);
        switch (id) {
            case 0: expectedText = "Ты потерял память. Принять вызов НЛО?"; break;
            case 1: expectedText = "Ты принял вызов. Поднимаешься на мостик к капитану?"; break;
            case 2: expectedText = "Ты поднялся на мостик. Ты кто?"; break;
        };
        assertEquals(expectedText, text);
    }

    @Test
    void checkNegativeIsLastQuestionById() {
        assertFalse(questService.isLastTaskById(questName, 1));
    }

    @Test
    void checkPositiveIsLastQuestionById() {
        assertTrue(questService.isLastTaskById(questName, 2));
    }

    @Test
    void checkExceptionQuestionTextById() {
        assertThrows(AdventureNotFoundException.class, ()->{questService.getTaskTextById(questName, 10);});
    }

    @Test
    void checkExceptionLastQuestionById() {
        assertThrows(AdventureNotFoundException.class, ()->{questService.isLastTaskById(questName, 10);});
    }

    @Test
    void checkGetAnswersByQuestionId() {
        List<AdventureAnswer> answers = new ArrayList<>();
        answers.add(new AdventureAnswer(1, "Подняться на мостик", 2));
        answers.add(new AdventureAnswer(2, "Отказаться подниматься на мостик", -2));
        assertEquals(answers.get(0).getAnswerText(), questService.getAnswersByTaskId(questName,1).get(0).getAnswerText());
        assertEquals(answers.get(1).getAnswerText(), questService.getAnswersByTaskId(questName,1).get(1).getAnswerText());
    }

    @Test
    void checkSaveAndGetUserByName() {
        String name = "Petya";
        Player user = new Player(name);
        questService.savePlayer(name, user);
        assertEquals(user.getPlayerName(), questService.getPlayerByName(name).getPlayerName());
    }

    @Test
    void checkNegativeIsExists() {
        assertFalse(questService.isPlayerExists("Vanya"));
    }

    @Test
    void checkPositiveIsExists() {
        Player user = new Player("Masha");
        questService.savePlayer("Masha", user);
        assertTrue(questService.isPlayerExists("Masha"));
    }
}
