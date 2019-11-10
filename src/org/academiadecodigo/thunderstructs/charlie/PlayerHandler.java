package org.academiadecodigo.thunderstructs.charlie;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import org.academiadecodigo.thunderstructs.charlie.Generators.*;
import org.academiadecodigo.thunderstructs.charlie.Utilities.Color;
import org.academiadecodigo.thunderstructs.charlie.Utilities.Messages;
import org.w3c.dom.ls.LSOutput;

import java.awt.*;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class PlayerHandler implements Runnable {

    private Socket playerSocket;
    private PrintWriter printToPlayer;
    private String name;
    private Team team;
    private Prompt prompt;
    private Game game;
    private String gameRoom;
    private int gameID;
    private boolean quit;
    private boolean gameOver;
    private String currentGameInfo = "";

    public PlayerHandler(Socket playerSocket) {

        this.playerSocket = playerSocket;
        gameOver = false;

        try {
            this.prompt = new Prompt(playerSocket.getInputStream(), new PrintStream(playerSocket.getOutputStream()));
            this.printToPlayer = new PrintWriter(playerSocket.getOutputStream(), true);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {

        try {
            this.name = MenuGenerator.askName(prompt);
            joinPlayerMap();

            int menuOption = -1;

            while (!quit) {
                if (!gameOver) {

                    if (menuOption == 3) {
                        menuOption = MenuGenerator.mainMenu(prompt, false);
                    } else {
                        menuOption = MenuGenerator.mainMenu(prompt, false);
                    }

                    playerRun(menuOption);
                    continue;
                }

                menuOption = MenuGenerator.mainMenu(prompt, false);
                playerRun(menuOption);
                gameOver = false;
            }
            exit();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Add this player to the server players list.
     */
    public void joinPlayerMap() {
        Server.getPlayers().put(name, this);
    }

    /**
     * Main menu screen is shown to the player allowing them to choose from the menu options.
     */
    public void playerRun(int menuOption) {

        try {
            switch (menuOption) {
                case 0:
                    quit = true;
                    break;
                case 1:
                    chooseGameRoom();
                    break;
                case 2:
                    createNewGame();
                    break;
                case 3:
                    printToPlayer.println(GFXGenerator.generateInfoBox(Messages.gameInstructions, TextAlign.LEFT));
                    break;
            }

            if (game != null) {
                joinGame();
            }

        } catch (InterruptedException ie) {
            System.err.println("Something went wrong with Count Down");
        }


    }

    /**
     *
     */
    public void chooseGameRoom() {

        while (team == null) {

            if ((game = gameToEnter()) == null) {
                return;
            }

            if (!game.hasEmptySlots()) {
                System.err.println("\n Game is full" + game.getPlayers().length);
                printToPlayer.println(Messages.GAME_FULL);
                continue;
            }

            System.out.println(gameRoom + " had space in game " + game.toString());
            team = chooseTeam();
        }
    }

    public Game gameToEnter() {

        gameRoom = MenuGenerator.joinGame(prompt);
        if (gameRoom == null) {
            return null;
        }

        return Server.getGames().get(gameRoom);
    }


    public void joinGame() throws InterruptedException {

        printToPlayer.println(name + " has joined " + team + " team in " + game.getName() + " game.\n");
        game.addPlayer(this);

        gameID = game.getGameCounter();

        printToPlayer.append("Waiting for players");
        printToPlayer.flush();


        while (game.getActivePlayers() != game.getNumMaxPlayers()) {

            printToPlayer.append(".");
            printToPlayer.flush();
            Thread.sleep(1000);

        }

        startCountDown(printToPlayer);

        while (game.getScore() > 0 && game.getScore() < 100) {
            // TODO: 08/11/2019 HARD: make it leave input when game over
            if (gameID != game.getGameCounter()) {
                reset();
                return;
            }
            sendChallenge(this);
        }

        winGame();
    }

    public Team chooseTeam() {
        return MenuGenerator.chooseTeam(prompt, game);
    }

    public void createNewGame() {

        boolean createGame = false;
        Game creatingGame = new Game(null, 0, null, 0, null, null, true);

        currentGameInfo = creatingGame.toString();
        // TODO: 09/11/2019 add current game info as part of Create game menu so it appears after DONT LOSE ROPE, instead of after input

        int menuChoice;
        menuChoice = MenuGenerator.createGameMenu(prompt, true, creatingGame);

        while (!createGame) {

            switch (menuChoice) {

                case -1:
                    System.err.println("chose: " + menuChoice);
                    if (!isAllSet(creatingGame)) {
                        break;
                    }
                    createGame = true;
                    break;

                case 0:
                    return;

                case 1:
                    creatingGame.setName(setGameName());
                    printToPlayer.println(GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle() +
                            "Game name set to: " + creatingGame.getName() + creatingGame.toString());
                    break;

                case 2:
                    creatingGame.setNumMaxPlayers(setPlayerAmount());
                    printToPlayer.println(GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle() +
                            "Max players set to: " + creatingGame.getNumMaxPlayers() + creatingGame.toString());
                    break;

                case 3:
                    selectTeam(creatingGame);
                    printToPlayer.println(GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle() +
                            "Team colors set to: " + Arrays.toString(creatingGame.getTeams()) + creatingGame.toString());
                    break;

                case 4:
                    GameType gameType;
                    if ((gameType = setGameType()) == null) {
                        printToPlayer.println(GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle());
                        break;
                    }
                    creatingGame.setGameType(gameType);
                    printToPlayer.println(GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle() +
                            "Game type set to: " + creatingGame.getGameType().toString() + creatingGame.toString());
                    break;

                case 5:
                    creatingGame.setDifficulty(setGameDifficulty());
                    printToPlayer.println(GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle() +
                            "Game difficulty set to: " + creatingGame.getDifficulty() + creatingGame.toString());
                    break;
                case 6:
                    printToPlayer.println(GFXGenerator.clearScreen() + GFXGenerator.generateInfoBox(Messages.createGameInstructions, TextAlign.LEFT));
                    break;
            }

            if (!createGame) {
                menuChoice = MenuGenerator.createGameMenu(prompt, false, creatingGame);
            }

        }

        creatingGame.setPlayers(creatingGame.getNumMaxPlayers());
        Server.getGames().put(creatingGame.getName(), creatingGame);

    }

    public boolean isAllSet(Game creatingGame) {
        System.err.println("entered is all set" + creatingGame.toString());
        if (creatingGame.getName() == null) {
            printToPlayer.println(GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle() + "Set game name!");
            return false;
        }

        if (creatingGame.getNumMaxPlayers() <= 1) {
            printToPlayer.println(GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle() + "Insert 2 or more players!");
            return false;
        }

        for (int i = 0; i < creatingGame.getTeams().length; i++) {
            if (creatingGame.getTeams()[i] == null) {
                printToPlayer.println(GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle() + "Please choose Team Colors!");
                return false;
            }
        }

        if (creatingGame.getGameType() == null) {
            printToPlayer.println(GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle() + "Set Game Type!");
            return false;
        }

        if (creatingGame.getDifficulty() == 0) {
            printToPlayer.println(GFXGenerator.clearScreen() + GFXGenerator.drawGameTitle() + "Set Game Difficulty!");
            return false;
        }

        return true;
    }

    private String setGameName() {

        try {
            return MenuGenerator.setGameName(prompt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int setPlayerAmount() {

        try {
            return MenuGenerator.setMaxNumbers(prompt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void selectTeam(Game creatingGame) {

        while (true) {

            int selectedTeam = MenuGenerator.selectTeam(prompt, creatingGame);

            switch (selectedTeam) {
                case 0:
                    return;

                case 1:
                    creatingGame.setTeam1(MenuGenerator.setTeamsColor(creatingGame, prompt));
                    break;

                case 2:
                    creatingGame.setTeam2(MenuGenerator.setTeamsColor(creatingGame, prompt));
                    break;
            }
        }
    }

    private GameType setGameType() {
        return MenuGenerator.setGameType(prompt);
    }

    private int setGameDifficulty() {
        return MenuGenerator.setGameDifficulty(prompt);
    }

    private void sendChallenge(PlayerHandler player) {

        switch (game.getGameType()) {
            case CALC:
                game.checkEquation(ChallengeGenerator.generateEquation(game.getDifficulty()), player);
                break;

            case WORDS:
                game.checkWord(ChallengeGenerator.generateWord(game.getDifficulty()), player);
                break;

            case MIXED:
                int randomChallenge = (int) (Math.random() * 10);
                if (randomChallenge < 5) {
                    game.checkEquation(ChallengeGenerator.generateEquation(game.getDifficulty()), player);
                    break;
                }
                game.checkWord(ChallengeGenerator.generateWord(game.getDifficulty()), player);
                break;
        }
    }

    public synchronized void winGame() {
        // TODO: this assumes that first player belongs to one team and that de following player will always be from the other team
        printToPlayer.println(GFXGenerator.drawRope(game.getScore(), game.getPlayers()[0].getTeam(), game.getPlayers()[1].getTeam()));
        gameOver = true;
        game.gameOver(this);
        reset();

        //int menuOption = MenuGenerator.mainMenu(prompt, false);
        //playerRun(menuOption);
    }

    public void reset() {
        team = null;
        game = null;
    }

    public void exit() throws IOException {

        printToPlayer.println(Messages.QUIT);
        Server.getPlayers().remove(name);
        playerSocket.close();
        Thread.currentThread().interrupt();
    }

    private void startCountDown(PrintWriter printToPlayer) throws InterruptedException {

        for (int i = 0; i < 4; i++) {

            switch (i) {
                case 0:
                    printToPlayer.println(GFXGenerator.drawCountDown(Count.READY));
                    Thread.sleep(1000);
                    break;
                case 1:
                    printToPlayer.println(GFXGenerator.drawCountDown(Count.TREE));
                    Thread.sleep(1000);
                    break;
                case 2:
                    printToPlayer.println(GFXGenerator.drawCountDown(Count.TWO));
                    Thread.sleep(1000);
                    break;
                case 3:
                    printToPlayer.println(GFXGenerator.drawCountDown(Count.ONE));
                    Thread.sleep(1000);
                    break;
            }

        }

    }

    public PrintWriter getOutputStream() {
        return printToPlayer;
    }

    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }

    public String getGameRoom() {
        return gameRoom;
    }

    public Prompt getPrompt() {
        return prompt;
    }
}
