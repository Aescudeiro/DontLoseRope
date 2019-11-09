package org.academiadecodigo.thunderstructs.charlie;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import org.academiadecodigo.thunderstructs.charlie.Generators.ChallengeGenerator;
import org.academiadecodigo.thunderstructs.charlie.Generators.Count;
import org.academiadecodigo.thunderstructs.charlie.Generators.GFXGenerator;
import org.academiadecodigo.thunderstructs.charlie.Generators.MenuGenerator;
import org.academiadecodigo.thunderstructs.charlie.Utilities.Color;
import org.academiadecodigo.thunderstructs.charlie.Utilities.Messages;

import java.awt.*;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayerHandler implements Runnable {

    private Socket playerSocket;
    private PrintWriter printToPlayer;
    private String name;
    private Team team;
    private Prompt prompt;
    private Game game;
    private int gameRoom;
    private boolean quit;

    public PlayerHandler(Socket playerSocket) {

        this.playerSocket = playerSocket;

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
            this.name = MenuGenerator.askName(playerSocket);
            joinPlayerMap();

            while (!quit) {
                playerRun();
            }
            exit();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void joinPlayerMap() {
        Server.getPlayers().put(name, this);
    }

    public void playerRun() {

        System.out.println("sending menu");
        int menuOption = MenuGenerator.mainMenu(prompt);
        System.out.println("menu sent");

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
                    printToPlayer.println(GFXGenerator.showGameInstructions());
                    break;
            }

            if (game != null) {
                joinGame();
            }

        } catch (InterruptedException ie){
            System.err.println("Something went wrong with Count Down");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        if (gameRoom == 0) {
            return null;
        }

        return Server.getGames().get(gameRoom);
    }

    public void joinGame() throws InterruptedException, IOException {

        printToPlayer.println(name + " has joined " + team + " team in " + game.toString() + " game.\n");
        game.addPlayer(this);

        // TODO: if a printToPlayers exists, why do we need the following:
        PrintWriter out = new PrintWriter(playerSocket.getOutputStream());


        while (game.getActivePlayers() != game.getNumMaxPlayers()) {
        }

        startCountDown(printToPlayer);

        while (game.getScore() > 0 && game.getScore() < 100) {
            // TODO: 08/11/2019 HARD: make it leave input when game over
            sendChallenge(this);
            if (game.getPlayers()[0] == null) {
                return;
            }
        }

        System.out.println("going to draw rope" + name);


        // TODO: this assumes that first player belongs to one team and that de following player will always be from the other team
        out.println(GFXGenerator.drawRope(game.getScore(), game.getPlayers()[0].getTeam(), game.getPlayers()[1].getTeam()));
        System.out.println("drew rope");
        game.gameOver(this);
        reset();
    }

    public Team chooseTeam() {
        return MenuGenerator.chooseTeam(prompt, game);
    }

    // TODO: create new game, allowing other gamers to join this game, maybe set a password for it. Needs a sub-menu asking for game name, type and to choose team allowed in the game created
    public void createNewGame(){

    }

    public void sendChallenge(PlayerHandler player) {

        switch (game.getGameType()) {
            case CALC:
                game.checkEquation(ChallengeGenerator.generateEquation(game.getDifficulty()), player);
                System.out.println(game.getScore());
                break;

            case WORDS:
                game.checkWord(ChallengeGenerator.generateWord(game.getDifficulty()), player);
                break;
        }
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

        for (int i = 0; i < 4; i++){

            switch(i){
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

    public int getGameRoom() {
        return gameRoom;
    }

    public Prompt getPrompt() {
        return prompt;
    }
}
