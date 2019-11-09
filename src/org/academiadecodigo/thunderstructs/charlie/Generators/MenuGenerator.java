package org.academiadecodigo.thunderstructs.charlie.Generators;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import org.academiadecodigo.thunderstructs.charlie.Game;
import org.academiadecodigo.thunderstructs.charlie.GameType;
import org.academiadecodigo.thunderstructs.charlie.Server;
import org.academiadecodigo.thunderstructs.charlie.Team;
import org.academiadecodigo.thunderstructs.charlie.Utilities.Messages;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;


public class MenuGenerator {

    public static String askName(Socket playerSocket) throws IOException {

        Scanner input = new Scanner(playerSocket.getInputStream());
        PrintWriter printWriter = new PrintWriter(playerSocket.getOutputStream(), true);

        printWriter.println(GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle() + Messages.WELCOME);

        return input.nextLine();

    }

    public static int mainMenu(Prompt prompt) {

        String[] menu = {"Join game", "Create game", "How to play", "Quit"};

        MenuInputScanner menuInputScanner = new MenuInputScanner(menu);
        menuInputScanner.setMessage(GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle() + Messages.MAIN_MENU);

        int choice = prompt.getUserInput(menuInputScanner);

        if (choice <= menu.length - 1) {
            return choice;
        }

        return 0;

    }

    public static int joinGame(Prompt prompt) {

        // TODO: 09/11/2019 add dynamic player number to game (gamename [players/maxplayers])

        String[] games = new String[Server.getGames().size() + 1];

        int counter = 0;
        for (Game game : Server.getGames().values()) {
            games[counter] = game.getName();
            counter++;
        }

        games[games.length - 1] = "Go back";

        MenuInputScanner menu = new MenuInputScanner(games);
        menu.setMessage(GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle() + Messages.JOIN_GAME);

        int choice = prompt.getUserInput(menu);

        if (choice < games.length) {
            return choice;
        }

        return 0;
    }

    public static int createGameMenu(Prompt prompt) {

        String[] menu = {"Set Game Name","Set Max Numbers", "Set Team Colors", "Set Game Type", "Set Game Difficulty", "Create Game",  "Go back"};

        MenuInputScanner menuInputScanner = new MenuInputScanner(menu);
        menuInputScanner.setMessage(GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle() + Messages.CREATE_MENU);

        int choice = prompt.getUserInput(menuInputScanner);

        if (choice == menu.length - 1) {
            return -1;
        }

        if (choice < menu.length - 1) {
            return choice;
        }

        return 0;
    }

    public static String setGameName(Socket playerSocket) throws IOException {

        Scanner input = new Scanner(playerSocket.getInputStream());
        PrintWriter printWriter = new PrintWriter(playerSocket.getOutputStream(), true);

        printWriter.println("Set game name: ");

        return input.nextLine();

    }

    public static int setMaxNumbers(Socket playerSocket) throws IOException {

        Scanner input = new Scanner(playerSocket.getInputStream());
        PrintWriter printWriter = new PrintWriter(playerSocket.getOutputStream(), true);

        printWriter.println("Set max player number: ");

        int maxNumber = Integer.parseInt(input.nextLine());

        if (maxNumber < 2) {
            setMaxNumbers(playerSocket);
        }

        return maxNumber;

    }

    public static int selectTeam(Prompt prompt){

        String[] teams = {"TEAM ONE","TEAM TWO","Go back"};

        MenuInputScanner menuInputScanner = new MenuInputScanner(teams);
        menuInputScanner.setMessage(GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle() + Messages.SELECT_TEAM);

        int choice = prompt.getUserInput(menuInputScanner);

        if (choice < teams.length){
            return choice;
        }

        return 0;
    }

    public static Team setTeamsColor(Game game, Prompt prompt) {

        Set<Team> teams = new HashSet<>(Arrays.asList(Team.values()));

        for (Team team : game.getTeams()) {
            teams.remove(team);
        }

        String[] choices = new String[teams.toArray().length + 1];

        for (int i = 1; i < choices.length; i++) {
            choices[i - 1] = teams.toArray()[i - 1].toString();
        }
        choices[choices.length - 1] = "Go back";

        MenuInputScanner menuInputScanner = new MenuInputScanner(choices);
        menuInputScanner.setMessage(GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle() + Messages.SET_TEAM_COLOR);

        int choice = prompt.getUserInput(menuInputScanner);

        if (choice < teams.toArray().length + 1) {
            System.out.println(teams.toArray()[choice - 1]);
            return (Team) teams.toArray()[choice - 1];
        }
        System.out.println("null");
        return null;
    }

    public static GameType setGameType(Prompt prompt) {

        String[] menu = new String[GameType.values().length + 1];

        for ( int i = 0; i < menu.length - 1; i++) {
            menu[i] = String.valueOf(GameType.values()[i]);
        }

        menu[menu.length - 1] = "Go back";

        MenuInputScanner menuInputScanner = new MenuInputScanner(menu);
        menuInputScanner.setMessage(GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle() + Messages.SET_GAME_TYPE);

        int choice = prompt.getUserInput(menuInputScanner);


        if (choice < GameType.values().length + 1) {
            System.out.println(GameType.values()[choice -1]);
            return GameType.values()[choice -1];
        }
        System.out.println("ola");
        return null;
    }

    public static int setGameDifficulty(Prompt prompt){

        String[] menu = {"1" , "2", "3", "4", "Go back"};

        MenuInputScanner menuInputScanner = new MenuInputScanner(menu);
        menuInputScanner.setMessage(GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle() + Messages.SET_DIFFICULTY);

        int choice = prompt.getUserInput(menuInputScanner);

        if (choice < menu.length - 1){
            return choice;
        }

        return 0;
    }

    public static Team chooseTeam(Prompt prompt, Game game) {

        String[] teams = game.getAvailableTeams();

        teams[teams.length - 1] = "Go back";

        MenuInputScanner menuInputScanner = new MenuInputScanner(teams);
        menuInputScanner.setMessage(GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle() + Messages.SET_TEAM_COLOR);

        int choice = prompt.getUserInput(menuInputScanner);

        Team[] teamTypes = game.getTeams();
        if (choice < teamTypes.length + 1) {

            return teamTypes[choice - 1];
        }

        return null;
    }

}