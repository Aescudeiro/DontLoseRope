package org.academiadecodigo.thunderstructs.charlie;
import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.thunderstructs.charlie.Generators.MenuGenerator;
import java.io.IOException;
import java.net.Socket;
public class Player implements Runnable {
    private Socket playerSocket;
    private String name;
    private Team team;
    private Prompt prompt;
    public Player(Socket playerSocket) {
        this.playerSocket = playerSocket;
    }
    @Override
    public void run() {
        try {
            this.name = MenuGenerator.askName(playerSocket);
            System.out.println(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getName() {
        return name;
    }