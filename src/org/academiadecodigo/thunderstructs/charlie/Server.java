package org.academiadecodigo.thunderstructs.charlie;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {

    public static final int TEAM_SCORE = 10;
    private ServerSocket serverSocket;
    private static Map<String, PlayerHandler> players = new HashMap<>();
    private static Map<Integer, Game> games = new HashMap<>();

    public Server(ServerSocket serverSocket) {

        this.serverSocket = serverSocket;

    }

    public void init() {

        try {

            ExecutorService s1 = Executors.newCachedThreadPool();

            while (serverSocket.isBound()) {

                System.out.println("Server: Waiting for player to join...");
                Socket playerSocket = serverSocket.accept();
                System.out.println(playerSocket.getInetAddress().toString());
                PlayerHandler playerHandler = new PlayerHandler(playerSocket);
                s1.submit(playerHandler);
                System.out.println("Server: New player joined.");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public static Map<Integer, Game> getGames() {
        return games;
    }

    public static Map<String, PlayerHandler> getPlayers() {
        return players;
    }

}