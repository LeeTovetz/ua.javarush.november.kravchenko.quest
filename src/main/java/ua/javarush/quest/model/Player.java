package ua.javarush.quest.model;

import lombok.Getter;

@Getter
public class Player {
    private final String playerName;
    private int gamesPlayed;
    private int gamesWon;

    public Player(String playerName) {
        this.playerName = playerName;
        this.gamesPlayed = 1;
        this.gamesWon = 0;
    }

    public void incrCountGames() {
        gamesPlayed++;
    }

    public void incrWin() {
        gamesWon++;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerName ='" + playerName + '\'' +
                ", gamesPlayed =" + gamesPlayed +
                ", gamesWon =" + gamesWon +
                '}';
    }
}
