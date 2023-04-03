package ua.javarush.quest.controller;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.InetAddress;

@Slf4j
@WebServlet(name = "startController", value = "/start")
public class StartController extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(true);

            String playerName = request.getParameter("playerName");
            request.setAttribute("playerName", playerName);
            session.setAttribute("IP", InetAddress.getLocalHost().getHostAddress());

            getServletContext()
                    .getRequestDispatcher("/adventure")
                    .forward(request, response);
        } catch (ServletException | IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
}