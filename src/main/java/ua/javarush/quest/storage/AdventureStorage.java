package ua.javarush.quest.storage;

import lombok.Getter;
import ua.javarush.quest.model.Adventure;

import java.util.HashMap;

@Getter
public class AdventureStorage {
    private final HashMap<String, Adventure> adventures = new HashMap<>();
}
