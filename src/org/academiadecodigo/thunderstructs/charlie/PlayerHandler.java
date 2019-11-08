package org.academiadecodigo.thunderstructs.charlie;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import org.academiadecodigo.thunderstructs.charlie.Generators.ChallengeGenerator;
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

        int menuOption = MenuGenerator.mainMenu(prompt);

        try {

            switch (menuOption) {
                case 0:
                    quit = true;
                    break;
                case 1:
                    joinGame();
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void joinGame() throws IOException {

        this.game = chooseGameRoom();
        this.team = MenuGenerator.chooseTeam(prompt, game);

        printToPlayer.println(name + " has joined " + team + " team in " + game.toString() + " game.");
        game.addPlayer(this);

        while (game.getActivePlayers() != game.getNumMaxPlayers()) {
        }

        while (game.getScore() > 0 && game.getScore() < 100) {
            sendChallenge(this);
        }

        PrintWriter out = new PrintWriter(playerSocket.getOutputStream());
        out.println(GFXGenerator.drawRope(game.getScore(), game.getPlayers()[0].getTeam(), game.getPlayers()[1].getTeam()));

        game.gameOver(this);
        reset();
    }

    public Game chooseGameRoom() {

        gameRoom = MenuGenerator.joinGame(prompt);
        game = Server.getGames().get(gameRoom);

        while (!game.hasEmptySlots()) {

            printToPlayer.println(Messages.GAME_FULL);
            gameRoom = MenuGenerator.joinGame(prompt);
            game = Server.getGames().get(gameRoom);

        }

        System.out.println(gameRoom + " had space in game " + game.toString());
        return Server.getGames().get(gameRoom);
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

        Server.getPlayers().remove(name);
        printToPlayer.println(Messages.QUIT);
        playerSocket.close();
        Thread.currentThread().interrupt();
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
