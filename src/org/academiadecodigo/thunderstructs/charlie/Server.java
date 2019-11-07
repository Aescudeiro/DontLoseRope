package org.academiadecodigo.thunderstructs.charlie;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {

    private ServerSocket serverSocket;
    private static Map<String, Player> players = new HashMap<>();
    private static Map<Integer, Game> games = new HashMap<>();

    private Server(ServerSocket serverSocket) {

        this.serverSocket = serverSocket;

    }

    public static void main(String[] args) {

        try {

            Server server = new Server(new ServerSocket(8080));
            Game g1 = new Game();
            Game g2 = new Game();

            games.put(1, g1);
            games.put(2, g2);
            server.init();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    private void init() {

        try {

            while (serverSocket.isBound()) {

                Socket playerSocket = serverSocket.accept();
                Player player = new Player(playerSocket);
                ExecutorService s1 = Executors.newCachedThreadPool();

                s1.submit(player);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public static Map<String, Player> getPlayers() {
        return players;
    }

    public static Map<Integer, Game> getGames() {
        return games;
    }
}