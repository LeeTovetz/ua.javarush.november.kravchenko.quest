package ua.javarush.quest.storage;

import lombok.Getter;
import ua.javarush.quest.model.Player;

import java.util.HashMap;
import java.util.Map;

@Getter
public class UserStorage {
    private final Map<String, Player> storage = new HashMap<>();
}
