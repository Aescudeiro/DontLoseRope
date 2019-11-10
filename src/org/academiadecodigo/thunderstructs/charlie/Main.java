package org.academiadecodigo.thunderstructs.charlie;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) {

        try {

            Server server = new Server(new ServerSocket(8080));

            Game g1 = new Game("2 Player calculation game (Difficulty 1)",2, GameType.CALC, 1, Team.RED, Team.GREEN, true);
            Game g2 = new Game("4 Player calculation game (Difficulty 4)",4, GameType.CALC, 4, Team.YELLOW, Team.BLUE, true);
            Game g3 = new Game("2 Player word game (Difficulty 2)",2, GameType.CALC, 2, Team.YELLOW, Team.BLUE, true);
            Game g4 = new Game("8 Player word game (Difficulty 4)",8, GameType.CALC, 4, Team.YELLOW, Team.BLUE, true);
            Game g5 = new Game("Make this a random challenge",2, GameType.CALC, 2, Team.RED, Team.BLUE, false);

            Server.getGames().put(1, g1);
            Server.getGames().put(2, g2);
            Server.getGames().put(3, g3);
            Server.getGames().put(4, g4);
            Server.getGames().put(5, g5);

            server.init();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}
