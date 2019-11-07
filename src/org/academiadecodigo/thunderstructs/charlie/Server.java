package org.academiadecodigo.thunderstructs.charlie;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {

    private ServerSocket serverSocket;
    private Socket playerSocket;
    private static Map<String, PlayerHandler> players = new HashMap<>();
    private static Set<Game> games = new HashSet<>();

    private Server(ServerSocket serverSocket) {

        this.serverSocket = serverSocket;

    }

    public static void main(String[] args) {

        try {

            Server server = new Server(new ServerSocket(8080));
            Game g1 = new Game();
            Game g2 = new Game();

            server.games.add(g1);
            server.games.add(g2);
            server.init();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    private void init() {

        try {

            while (serverSocket.isBound()) {

                this.playerSocket = serverSocket.accept();

                PlayerHandler playerHandler = new PlayerHandler(playerSocket);

                ExecutorService s1 = Executors.newCachedThreadPool();

                s1.submit(playerHandler);

                players.put(playerHandler.getName(), playerHandler);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public static Map<String, PlayerHandler> getPlayers() {
        return players;
    }

}