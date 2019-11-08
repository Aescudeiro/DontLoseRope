package org.academiadecodigo.thunderstructs.charlie.Generators;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import org.academiadecodigo.thunderstructs.charlie.Server;
import org.academiadecodigo.thunderstructs.charlie.Team;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;


public class MenuGenerator {

    public static String askName(Socket playerSocket) throws IOException {

        Scanner input = new Scanner(playerSocket.getInputStream());

        PrintWriter printWriter = new PrintWriter(playerSocket.getOutputStream(), true);

        printWriter.println(GFXGenerator.drawGameTitle() + "\n WELCOME PLAYER! Please insert your name: ");

        return input.nextLine();

    }

    public static int joinGame(Prompt prompt) {

        String[] games = new String[Server.getGames().size()];

        Arrays.fill(games, "Game");

        MenuInputScanner menu = new MenuInputScanner(games);

        menu.setMessage("Choose your game: ");

        return prompt.getUserInput(menu);
    }

    public static Team chooseTeam(Prompt prompt) {

        String[] teams = new String[Team.values().length];

        for(int i = 0; i < teams.length; i++){
            teams[i] = Team.values()[i].toString();
        }

        MenuInputScanner menuInputScanner = new MenuInputScanner(teams);

        int choice = prompt.getUserInput(menuInputScanner);

        switch (choice) {
            case 1:
                return Team.RED;

            case 2:
                return Team.BLUE;
        }

        return null;
    }

}