package org.academiadecodigo.thunderstructs.charlie;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
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
    }public static void main(String[] args) {

        try {
            Scanner input = new Scanner(System.in);
            System.out.print("Pick a port to host your server: ");
            String port = input.nextLine();

            Server server = new Server(new ServerSocket(Integer.parseInt(port)));

            Game g1 = new Game("2 Player calculation game (Difficulty 1)",2, GameType.CALC, 1, Team.RED, Team.GREEN, true);
            Game g2 = new Game("4 Player calculation game (Difficulty 4)",4, GameType.CALC, 4, Team.YELLOW, Team.BLUE, true);
            Game g3 = new Game("2 Player word game (Difficulty 2)",2, GameType.WORDS, 2, Team.YELLOW, Team.BLUE, true);
            Game g4 = new Game("8 Player word game (Difficulty 4)",8, GameType.WORDS, 4, Team.YELLOW, Team.BLUE, true);
            Game g5 = new Game("2 Player random game (Difficulty 4)",2, GameType.MIXED, 4, Team.RED, Team.BLUE, false);

            games.put(1, g1);
            games.put(2, g2);
            games.put(3, g3);
            games.put(4, g4);
            games.put(5, g5);

            server.init();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }




}