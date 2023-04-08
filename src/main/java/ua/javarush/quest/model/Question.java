package ua.javarush.quest.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Question {
    private int id;
    private String text;
    private boolean isLastQuestion;
    private List<AdventureAnswer> answers;
}
