package org.academiadecodigo.thunderstructs.charlie.Generators;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import org.academiadecodigo.thunderstructs.charlie.Game;
import org.academiadecodigo.thunderstructs.charlie.GameType;
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

    public static int mainMenu(Prompt prompt) {

        String[] menu = {"Join game","Quit"};

        MenuInputScanner menuInputScanner = new MenuInputScanner(menu);

        int choice = prompt.getUserInput(menuInputScanner);

        if(choice <= menu.length -1){
            return choice;
        }

        return 0;

    }

    public static int joinGame(Prompt prompt) {

        String[] games = new String[Server.getGames().size() + 1];

        int counter = 1;
        for (Game s : Server.getGames().values()) {
                games[counter-1] = s.getGameType().toString().substring(0,1) +
                        s.getGameType().toString().substring(1).toLowerCase() + " game";
            counter++;
        }

        games[games.length - 1] = "Go back";

        MenuInputScanner menu = new MenuInputScanner(games);

        menu.setMessage("Choose your game: ");

        int choice = prompt.getUserInput(menu);

        if ( choice <= games.length - 2 ) {
            return choice;
        }

        return 0;
    }

    public static Team chooseTeam(Prompt prompt) {
        System.out.println("entered choose team");

        String[] teams = new String[Team.values().length + 1];

        for (int i = 0; i < teams.length - 1; i++) {
            teams[i] = Team.values()[i].toString();
            System.out.println(teams[i]);
        }

        teams[teams.length - 1] = "Go back";

        MenuInputScanner menuInputScanner = new MenuInputScanner(teams);

        System.out.println("waiting for user to choose team");
        int choice = prompt.getUserInput(menuInputScanner);

        Team[] teamTypes = Team.values();

        if (choice < teamTypes.length + 1) {
            return teamTypes[choice - 1];
        }

        return null;

    }

}