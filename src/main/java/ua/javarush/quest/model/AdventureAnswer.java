package ua.javarush.quest.model;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdventureAnswer {
    private int id;
    private String text;
    private Integer nextQuestionId;
}
