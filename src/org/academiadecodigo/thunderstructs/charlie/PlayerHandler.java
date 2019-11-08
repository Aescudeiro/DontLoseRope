package org.academiadecodigo.thunderstructs.charlie;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import org.academiadecodigo.thunderstructs.charlie.Generators.MenuGenerator;
import org.academiadecodigo.thunderstructs.charlie.Utilities.Messages;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayerHandler implements Runnable {

    private Socket playerSocket;
    private StringInputScanner stringInputScanner;
    private PrintWriter printToPlayer;
    private String name;
    private Team team;
    private Prompt prompt;
    private Game game;

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
            this.game = chooseGameRoom();
            this.team = MenuGenerator.chooseTeam(prompt);
            printToPlayer.println(name + " has joined " + team + " team in " + game.toString() + " game.");
            game.addPlayer(this);
            game.init(this);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void joinPlayerMap() {
        Server.getPlayers().put(name,this);
    }


    public Game chooseGameRoom() {

        int gameRoom = MenuGenerator.joinGame(prompt);
        Game game = Server.getGames().get(gameRoom);

        while (!game.hasEmptySlots()) {

            printToPlayer.println(Messages.GAME_FULL);
            gameRoom = MenuGenerator.joinGame(prompt);
            game = Server.getGames().get(gameRoom);

        }

        System.out.println(gameRoom + " had space in game " + game.toString());
        return Server.getGames().get(gameRoom);
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

    public Prompt getPrompt() {
        return prompt;
    }

    public StringInputScanner getStringInputScanner() {
        return stringInputScanner;
    }
}
