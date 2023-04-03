package ua.javarush.quest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class Adventure {
    private String questName;
    private List<Task> tasks;
}
