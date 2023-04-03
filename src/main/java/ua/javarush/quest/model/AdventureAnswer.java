package ua.javarush.quest.model;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdventureAnswer {
    private int answerId;
    private String answerText;
    private Integer nextQuestionId;
}
