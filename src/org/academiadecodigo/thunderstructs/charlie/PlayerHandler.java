package org.academiadecodigo.thunderstructs.charlie;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.thunderstructs.charlie.Generators.MenuGenerator;
import org.academiadecodigo.thunderstructs.charlie.Utilities.Messages;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class PlayerHandler implements Runnable {

    private Socket playerSocket;
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
            this.team = MenuGenerator.chooseTeam(prompt);
            game.addPlayer(this);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void joinPlayerMap() {
        Server.getPlayers().put(name,this);
    }


    public Game chooseGameRoom() {

        int gameRoom = MenuGenerator.joinGame(prompt);

        while (Server.getGames().get(gameRoom).hasEmptySlots()) {
            gameRoom = MenuGenerator.joinGame(prompt);
            System.out.println(Messages.GAME_FULL);
        }

        return Server.getGames().get(gameRoom);
    }


    public OutputStream getOutputStream() throws IOException {
        return playerSocket.getOutputStream();
    }

    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }
}
