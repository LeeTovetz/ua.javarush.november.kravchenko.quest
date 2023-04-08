package ua.javarush.quest.logic;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import ua.javarush.quest.model.AdventureAnswer;
import ua.javarush.quest.model.Quest;
import ua.javarush.quest.model.Question;
import ua.javarush.quest.model.Player;
import ua.javarush.quest.exception.QuestionNotFoundException;
import ua.javarush.quest.storage.QuestStorage;
import ua.javarush.quest.storage.UserStorage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class QuestLogic {
    private final QuestStorage questStorage;
    private final UserStorage userStorage;

    public QuestLogic() {
        this.questStorage = new QuestStorage();
        this.userStorage = new UserStorage();
    }

    public String addQuestAndReturnName(String fileName) throws IOException {
        Quest quest = getQuestFromFile(fileName);
        String questName = quest.getQuestName();
        questStorage.getQuests().put(questName, getQuestFromFile(fileName));
        return questName;
    }

    public String getQuestionTextById(String questName, int id) {
        Optional<Question> question = getQuestion(questName, id);
        if (question.isPresent()) {
            return question.get().getText();
        } else {
            throw new QuestionNotFoundException("Question for quest = " + questName + " and id = " + id + " not found");
        }

    }

    public boolean isLastQuestionById(String questName, int id) {
        Optional<Question> question = getQuestion(questName, id);
        if (question.isPresent()) {
            return question.get().isLastQuestion();
        } else {
            throw new QuestionNotFoundException("Question for quest = " + questName + " and id = " + id + " not found");
        }
    }

    public List<AdventureAnswer> getAnswersByQuestionId(String questName, int id) {
        return questStorage.getQuests().get(questName).getQuestions().get(id).getAnswers();
    }

    public void saveUser(String name, Player user) {
        userStorage.getStorage().put(name, user);
    }

    public Player getUserByName(String name) {
        return userStorage.getStorage().get(name);
    }

    public boolean isUserExists(String name) {
        return userStorage.getStorage().containsKey(name);
    }

    private Optional<Question> getQuestion(String questName, int id) {
        return questStorage.getQuests().get(questName).getQuestions().stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    private Quest getQuestFromFile(String fileName) throws IOException {
        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).getFile());
        return new JsonMapper().readValue(file, Quest.class);
    }
}
