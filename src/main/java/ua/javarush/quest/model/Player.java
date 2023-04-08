package ua.javarush.quest.model;

import lombok.Getter;

@Getter
public class Player {
    private final String name;
    private int countGames;
    private int countWin;

    public Player(String name) {
        this.name = name;
        this.countGames = 1;
        this.countWin = 0;
    }

    public void incrCountGames() {
        countGames++;
    }

    public void incrWin() {
        countWin++;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", countGames=" + countGames +
                ", countWin=" + countWin +
                '}';
    }
}
