package org.academiadecodigo.thunderstructs.charlie;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.thunderstructs.charlie.Generators.MenuGenerator;
import org.academiadecodigo.thunderstructs.charlie.Utilities.Messages;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class PlayerHandler implements Runnable {

    private Socket playerSocket;
    private String name;
    private Team team;
    private Prompt prompt;

    public PlayerHandler(Socket playerSocket) {

        this.playerSocket = playerSocket;

    }

    @Override
    public void run() {

        try {
            this.name = MenuGenerator.askName(playerSocket);
            joinPlayerMap();
            chooseGameRoom();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getName() {
        return name;
    }

    public void joinPlayerMap() {
        Server.getPlayers().put(name,this);
    }

    public void chooseGameRoom() {

        int gameRoom;

        while (!game.hasEmptySlot()) {
            gameRoom = MenuGenerator.joinGame(prompt);
            System.out.println(Messages.GAME_FULL);
        }

        if ()

    }

    public OutputStream getOutputStream() throws IOException {
        return playerSocket.getOutputStream();
    }

}