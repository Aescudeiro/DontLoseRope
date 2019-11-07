package org.academiadecodigo.thunderstructs.charlie;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.thunderstructs.charlie.Utilities.Messages;

public class Game {

    private int score;
    private int numMaxPlayers;
    private Player[] players;
    private Prompt prompt;
    private GameType gameType;


    public Game(int num, GameType type) {

        score = 50;

        numMaxPlayers = num;
        gameType = type;
        players = new Player[numMaxPlayers];

    }

    public void init() {

        while (score <= 0 && score >= 100) {

            for(Player p : players) {

                switch(gameType) {

                    case CALC:
                        break;

                    case WORDS:
                        break;
                }

                p.getOutputStream().println();

            }

        }

        winner(score);
    }

    public void addPlayer(Player player) {

        for (int i = 0; i < numMaxPlayers; i++) {

            if (players[i] == null) {
                players[i] = player;
                break;
            }
        }
    }

    public boolean hasEmptySlots() {

        for (Player p : players) {
            if(p == null) {
                return true;
            }
        }

        return false;
    }

    public void winner(int score) {
        switch (score) {
            case 0:
                announceWinner(Team.BLUE);
                break;
            case 100:
                announceWinner(Team.RED);
                break;
        }
    }

    public void announceWinner(Team team) {

        for (Player p : players) {
            p.getOutputStream().println(Messages.WINNER_ANNOUNCEMENT + " " + team.getTeam());
        }

    }


    public static void main(String[] args) {



    }

}