package ua.javarush.quest.storage;

import lombok.Getter;
import ua.javarush.quest.model.Quest;

import java.util.HashMap;

@Getter
public class QuestStorage {
    private final HashMap<String, Quest> quests = new HashMap<>();
}
