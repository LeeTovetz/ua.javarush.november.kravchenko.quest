package ua.javarush.quest.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Task {
    private int id;
    private String text;
    private boolean isLastTask;
    private List<AdventureAnswer> answers;
}
