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
    private String name;
    private Team team;
    private Prompt prompt;
    private Game game;

    public PlayerHandler(Socket playerSocket) {

        this.playerSocket = playerSocket;
        try {
            this.prompt = new Prompt(playerSocket.getInputStream(), new PrintStream(playerSocket.getOutputStream()));
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
            System.out.println("chose game room");
            this.team = MenuGenerator.chooseTeam(prompt);
            System.out.println("chose team");
            game.addPlayer(this);
            System.out.println("added player");
            game.init();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void joinPlayerMap() {
        Server.getPlayers().put(name,this);
    }


    public Game chooseGameRoom() {

        int gameRoom = MenuGenerator.joinGame(prompt);
        System.out.println(gameRoom);

        Game game = Server.getGames().get(gameRoom);

        while (!game.hasEmptySlots()) {
            System.out.println("in loop");
            //sout needs to go to player
            System.out.println(Messages.GAME_FULL);
            gameRoom = MenuGenerator.joinGame(prompt);
            //make him be able to go to other room
        }

        System.out.println("game had space!");
        return Server.getGames().get(gameRoom);
    }


    public PrintWriter getOutputStream() throws IOException {
        return new PrintWriter(playerSocket.getOutputStream());
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
