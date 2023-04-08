package ua.javarush.quest.logic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ua.javarush.quest.model.AdventureAnswer;
import ua.javarush.quest.model.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestServiceTest {
    QuestLogic questService = new QuestLogic();
    private String questName;

    public QuestServiceTest() throws IOException {
        questName = questService.addQuestAndReturnName("quest.json");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void checkGetQuestionTextById(int id) {
        String expectedText = "";
        String text = questService.getQuestionTextById(questName, id);
        switch (id) {
            case 0:
                expectedText = "Ты находишься на пристани и видишь корабль с черепом и костями на флаге. Присоединиться к команде пиратов?";
                break;
            case 1:
                expectedText = "Ты присоединился к команде. Встретиться с капитаном?";
                break;
            case 2:
                expectedText = "Ты встретился с капитаном. Какое имя ты скажешь ему?";
                break;
        }
        ;
        assertEquals(expectedText, text);
    }

    @Test
    void checkNegativeIsLastQuestionById() {
        assertFalse(questService.isLastQuestionById(questName, 2));
    }

    @Test
    void checkGetAnswersByQuestionId() {
        List<AdventureAnswer> answers = new ArrayList<>();
        answers.add(new AdventureAnswer(1, "Встретиться с капитаном", 2));
        answers.add(new AdventureAnswer(2, "Не встречаться с капитаном", -2));
        assertEquals(answers.get(0).getText(), questService.getAnswersByQuestionId(questName, 1).get(0).getText());
        assertEquals(answers.get(1).getText(), questService.getAnswersByQuestionId(questName, 1).get(1).getText());
    }

    @Test
    void checkSaveAndGetUserByName() {
        String name = "Petya";
        Player user = new Player(name);
        questService.saveUser(name, user);
        assertEquals(user.getName(), questService.getUserByName(name).getName());
    }

    @Test
    void checkNegativeIsExists() {
        assertFalse(questService.isUserExists("Vanya"));
    }

    @Test
    void checkPositiveIsExists() {
        Player user = new Player("Masha");
        questService.saveUser("Masha", user);
        assertTrue(questService.isUserExists("Masha"));
    }
}
