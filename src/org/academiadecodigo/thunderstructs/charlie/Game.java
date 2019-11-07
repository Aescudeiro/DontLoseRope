package org.academiadecodigo.thunderstructs.charlie;

import org.academiadecodigo.thunderstructs.charlie.Utilities.Messages;

public class Game {

    private int score;
    private int numMaxPlayers;
    private Player[] players;

    public Game(int num) {
        score = 50;
        numMaxPlayers = num;
        players = new Player[numMaxPlayers];
    }

    public void init() {

        while (score <= 0 && score >= 100) {


        }

        winner(score);
    }

    public void initializePlayers(Player player) {

        for (Player p : players) {

            if (p == null) {
                p = player;
                break;
            }

        }

    }

    public void hasEmptySlots() {

        for (Player p : players) {

        }

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


}