package ua.javarush.quest.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ua.javarush.quest.model.AdventureAnswer;
import ua.javarush.quest.model.Player;
import ua.javarush.quest.exception.AdventureNotFoundException;
import ua.javarush.quest.logic.AdventureLogic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/adventure")
@Slf4j
@Getter
public class AdventureController extends HttpServlet {
    private AdventureLogic adventureLogic;
    private String adventureName;

    @Override
    public void init() {
        try {
            adventureLogic = new AdventureLogic();
            adventureName = adventureLogic.addAdventureAndReturnName("quest.json");
            log.info("Creating adventureStorage and playerStorage");
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String playerName = request.getParameter("playerName");
        Player player;

        if (!adventureLogic.isPlayerExists(playerName)) {
            player = new Player(playerName);
            adventureLogic.savePlayer(playerName, player);
            log.info("Creating new player " + playerName);
        } else {
            player = adventureLogic.getPlayerByName(playerName);
            log.info("Player " + playerName + " exists");
        }

        try {
            int nextQuestionId = Integer.parseInt(request.getParameter("nextQuestionId"));
            boolean isLastQuestion = Boolean.parseBoolean(request.getParameter("isLastQuestion"));
            boolean isWrongAnswer = checkNegativeNumber(nextQuestionId);
            String questionText = adventureLogic.getTaskTextById(adventureName, nextQuestionId);

            log.info(player.toString() + ", nextQuestionId = " + nextQuestionId + ", isLastQuestion = " + isLastQuestion +
                    ", isWrongAnswer = " + isWrongAnswer + ", questionText = " + questionText);

            if (!isLastQuestion && !isWrongAnswer){
                List<AdventureAnswer> answersByQuestion = adventureLogic.getAnswersByTaskId(adventureName, nextQuestionId);
                isLastQuestion = adventureLogic.isLastTaskById(adventureName, nextQuestionId);
                request.setAttribute("questionText", questionText);
                request.setAttribute("answers", answersByQuestion);
                request.setAttribute("nextQuestionId", nextQuestionId);
                request.setAttribute("isLastQuestion", isLastQuestion);
                request.setAttribute("playerName", player.getPlayerName());
                request.setAttribute("gamesPlayed", player.getGamesPlayed());
                request.setAttribute("gamesWon", player.getGamesWon());
                request.getRequestDispatcher("adventure.jsp").forward(request, response);
            } else if (isLastQuestion && !isWrongAnswer) {
                player.incrCountGames();
                player.incrWin();
                request.setAttribute("text", questionText);
                log.info(player.getPlayerName() + " won! " + " gamesPlayed = " + player.getGamesPlayed() + ". gamesWon = " + player.getGamesWon());
                request.getRequestDispatcher("final.jsp").forward(request, response);
            } else {
                player.incrCountGames();
                request.setAttribute("text", questionText);
                log.info(player.getPlayerName() + " lost! " + " gamesPlayed = " + player.getGamesPlayed() + ". gamesWon = " + player.getGamesWon());
                request.getRequestDispatcher("final.jsp").forward(request, response);
            }
        } catch (ServletException | AdventureNotFoundException | IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean checkNegativeNumber(int nextQuestionId) {
        return nextQuestionId < 0;
    }
}