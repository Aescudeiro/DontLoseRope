package org.academiadecodigo.thunderstructs.charlie.Generators;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import org.academiadecodigo.thunderstructs.charlie.Server;
import org.academiadecodigo.thunderstructs.charlie.Team;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class MenuGenerator {

    public static String askName(Socket playerSocket) throws IOException {

        Scanner input = new Scanner(playerSocket.getInputStream());

        PrintWriter printWriter = new PrintWriter(playerSocket.getOutputStream(), true);

        printWriter.println("Set your username: ");

        return input.nextLine();

    }

    public static int joinGame(Prompt prompt) {

        String[] games = new String[Server.getGames().size()];

        for ( String game : games ){
            game = "Game";
            System.out.println(game);
        }

        MenuInputScanner menu = new MenuInputScanner(games);

        menu.setMessage("Pick your game: ");

        return prompt.getUserInput(menu);
    }

    public static Team chooseTeam(Prompt prompt) {

        String[] teams = {"Red", "Blue"};

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