package ua.javarush.quest.logic;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import ua.javarush.quest.model.AdventureAnswer;
import ua.javarush.quest.model.Adventure;
import ua.javarush.quest.model.Task;
import ua.javarush.quest.model.Player;
import ua.javarush.quest.exception.AdventureNotFoundException;
import ua.javarush.quest.storage.AdventureStorage;
import ua.javarush.quest.storage.PlayerStorage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class AdventureLogic {
    private final AdventureStorage AdventureStorage;
    private final PlayerStorage PlayerStorage;

    public AdventureLogic() {
        this.AdventureStorage = new AdventureStorage();
        this.PlayerStorage = new PlayerStorage();
    }

    public String addAdventureAndReturnName(String fileName) throws IOException {
        Adventure adventure = getAdventureFromFile(fileName);
        String adventureName = adventure.getQuestName();
        AdventureStorage.getAdventures().put(adventureName, getAdventureFromFile(fileName));
        return adventureName;
    }

//    public String addAdventureAndReturnName(String fileName) throws IOException {
//        Adventure adventure = getAdventureFromFile(fileName);
//        String adventureName = adventure.getQuestName();
//        AdventureStorage.getAdventures().put(adventureName, adventure);
//        return adventureName;
//    }

    public String getTaskTextById(String adventureName, int answerId) {
        Optional<Task> task = getTask(adventureName, answerId);
        if (task.isPresent()) {
            return task.get().getText();
        } else {
            throw new AdventureNotFoundException("Task for adventure = " + adventureName + " and answerId = " + answerId + " not found");
        }

    }

    public boolean isLastTaskById(String adventureName, int answerId) {
        Optional<Task> task = getTask(adventureName, answerId);
        if (task.isPresent()) {
            return task.get().isLastTask();
        } else {
            throw new AdventureNotFoundException("Task for adventure = " + adventureName + " and answerId = " + answerId + " not found");
        }
    }

    public List<AdventureAnswer> getAnswersByTaskId(String questName, int answerId) {
        return AdventureStorage.getAdventures().get(questName).getTasks().get(answerId).getAnswers();
    }

    public void savePlayer(String name, Player player) {
        PlayerStorage.getRepository().put(name, player);
    }

    public Player getPlayerByName(String name) {
        return PlayerStorage.getRepository().get(name);
    }

    public boolean isPlayerExists(String name) {
        return PlayerStorage.getRepository().containsKey(name);
    }

    private Optional<Task> getTask(String adventureName, int id) {
        return AdventureStorage.getAdventures().get(adventureName).getTasks().stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    private Adventure getAdventureFromFile(String fileName) throws IOException {
        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).getFile());
        return new JsonMapper().readValue(file, Adventure.class);
    }
}
