package org.academiadecodigo.thunderstructs.charlie.Generators;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import org.academiadecodigo.thunderstructs.charlie.Game;
import org.academiadecodigo.thunderstructs.charlie.GameType;
import org.academiadecodigo.thunderstructs.charlie.Server;
import org.academiadecodigo.thunderstructs.charlie.Team;
import org.academiadecodigo.thunderstructs.charlie.Utilities.Color;
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

    public static int mainMenu(Prompt prompt, boolean hasGameTitle) {

        String[] menu = {"Join game", "Create game", "How to play", "Quit"};

        String msg = Messages.MAIN_MENU;
        if(hasGameTitle){
            msg = GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle() + Messages.MAIN_MENU;
        }

        int choice = buildMenu(prompt, msg, menu);

        if (choice <= menu.length - 1) {
            return choice;
        }

        return 0;

    }

    public static int menuAfterMatch(Prompt prompt) {

        String[] menu = {"Join game", "Create game", "How to play", "Quit"};

        int choice = buildMenu(prompt, Messages.MAIN_MENU, menu);

        if (choice <= menu.length - 1) {
            return choice;
        }

        return 0;

    }

    public static int joinGame(Prompt prompt) {

        // TODO: 09/11/2019 add dynamic player number to game (gamename [players/maxplayers])

        String[] games = new String[Server.getGames().size() + 1];
        games[games.length - 1] = "Go back";

        int counter = 0;
        for (Game game : Server.getGames().values()) {
            games[counter] = game.getName();
            counter++;
        }

        String msg = GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle() + Messages.JOIN_GAME;

        int choice = buildMenu(prompt, msg, games);

        if (choice < games.length) {
            return choice;
        }

        return 0;
    }

    public static int createGameMenu(Prompt prompt, boolean hasGameTitle, Game game) {

        String color = Color.ANSI_YELLOW;
        String resetColor = Color.ANSI_RESET;

        String[] menu = {
                "Set Game Name" + ((game.getName() != null) ? color + "(" + game.getName() + ")" + resetColor : ""),
                "Set Max Numbers" + ((game.getNumMaxPlayers()  != 0) ? color + "(" + game.getNumMaxPlayers() + ")" + resetColor : ""),
                "Set Team Colors" + ((game.getTeams().length  != 0) ? color + "(" + game.getTeams()[0].toString() + ", " + game.getTeams()[1].toString() + ")" + resetColor : ""),
                "Set Game Type" + ((game.getGameType()  != null) ? color + "(" + game.getGameType().toString() + ")" + resetColor : ""),
                "Set Game Difficulty" + ((game.getDifficulty()  != 0) ? color + "(" + game.getDifficulty() + ")" + resetColor : ""),
                "Create Game",
                "Cancel"
        };

        String msg = Messages.CREATE_MENU;
        if(hasGameTitle){
            msg = GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle() + Messages.CREATE_MENU;
        }

        int choice = buildMenu(prompt, msg, menu);

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

    public static int selectTeam(Prompt prompt, Game game){

        String color = Color.ANSI_YELLOW;
        String resetColor = Color.ANSI_RESET;

        String[] teams = {
                "TEAM ONE" + ((game.getTeams()[0]  != null) ? color + "(" + game.getTeams()[0].toString() + resetColor : ""),
                "TEAM TWO" + ((game.getTeams()[1]  != null) ? color + "(" + game.getTeams()[1].toString() + resetColor : ""),
                "Go back"};

        String msg = GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle() + Messages.SELECT_TEAM;

        int choice = buildMenu(prompt, msg, teams);

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
        choices[choices.length - 1] = "Reset color";

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
        menu[menu.length - 1] = "Go back";

        int counter = 0;
        for(GameType gt : GameType.values()){
            menu[counter] = gt.toString();
            counter++;
        }

        String msg = GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle() + Messages.SET_GAME_TYPE;

        int choice = buildMenu(prompt, msg, menu);

        if (choice < GameType.values().length + 1) {
            System.out.println(GameType.values()[choice -1]);
            return GameType.values()[choice -1];
        }
        return null;
    }

    public static int setGameDifficulty(Prompt prompt){

        String[] menu = {"Easy" , "Normal", "Hard", "SUPER", "Go back"};

        String msg = GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle() + Messages.SET_DIFFICULTY;

        int choice = buildMenu(prompt, msg, menu);

        if (choice < menu.length){
            return choice;
        }

        return 0;
    }

    public static synchronized Team chooseTeam(Prompt prompt, Game game) {
        if(game.getActivePlayers() == game.getNumMaxPlayers()){
            return null;
        }

        String[] teams = game.getAvailableTeams();
        teams[teams.length - 1] = "Go back";
        String msg = GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle() + Messages.SET_TEAM_COLOR;

        int choice = buildMenu(prompt, msg, teams);

        Team[] teamTypes = game.getTeams();

        if(teams.length == 2) {
            for (Team t : teamTypes) {
                if (t.toString().equals(teams[0])){
                    return game.getTeams()[0];
                }
                return game.getTeams()[1];
            }
        }

        if (choice < teamTypes.length + 1) {

            return teamTypes[choice - 1];
        }

        return null;
    }

    private static int buildMenu(Prompt prompt, String menuMessage, String [] menuOptions){
        MenuInputScanner menu = new MenuInputScanner(menuOptions);
        menu.setMessage(menuMessage);
        return prompt.getUserInput(menu);
    }

}