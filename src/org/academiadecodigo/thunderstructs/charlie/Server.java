package org.academiadecodigo.thunderstructs.charlie;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {

    public static final int TEAM_SCORE = 10;
    private ServerSocket serverSocket;
    private static Map<String, PlayerHandler> players = new HashMap<>();
    private static volatile Map<String, Game> games = new LinkedHashMap<>();

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

    public static Map<String, Game> getGames() {
        return games;
    }

    public static Map<String, PlayerHandler> getPlayers() {
        return players;
    }

    public static void main(String[] args) {

        try {
            Scanner input = new Scanner(System.in);
            System.out.print("Pick a port to host your server: ");
            String port = input.nextLine();

            Server server = new Server(new ServerSocket(Integer.parseInt(port)));

            Game g1 = new Game("Calculation game (Difficulty 1)",2, GameType.CALC, 1, Team.RED, Team.GREEN, false);
            Game g2 = new Game("Word game (Difficulty 2)",2, GameType.WORDS, 2, Team.YELLOW, Team.BLUE, false);
            Game g3 = new Game("Brainfuck?",2, GameType.MIXED, 4, Team.YELLOW, Team.BLUE, false);

            games.put(g1.getName(), g1);
            games.put(g2.getName(), g2);
            games.put(g3.getName(), g3);

            server.init();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }




}