package org.academiadecodigo.thunderstructs.charlie;

import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import org.academiadecodigo.thunderstructs.charlie.Generators.GFXGenerator;
import org.academiadecodigo.thunderstructs.charlie.Utilities.Messages;


public class Game {

    private static final int MAX_SCORE = 100;
    private volatile int score;
    private int numMaxPlayers;
    private int difficulty;
    private volatile int activePlayers;

    private PlayerHandler[] players;
    private GameType gameType;
    private Team[] teams = new Team[2];


    public Game(int numMaxPlayers, GameType type, int difficulty, Team team1, Team team2) {

        score = MAX_SCORE / 2;
        activePlayers = 0;

        teams[0] = team1;
        teams[1] = team2;

        this.numMaxPlayers = numMaxPlayers;
        gameType = type;
        this.difficulty = difficulty;

        players = new PlayerHandler[numMaxPlayers];

    }



    public synchronized void addPlayer(PlayerHandler player) {

        if (activePlayers < numMaxPlayers) {
            players[activePlayers] = player;
            System.out.println(player.getName() + " added to game as player " + (activePlayers + 1));
            activePlayers++;
            return;
        }

        System.out.println("This room (" + this.toString() + ") is full");
    }

    public boolean hasEmptySlots() {

        for (PlayerHandler p : players) {
            if (p == null) {
                System.err.println(this.toString() + ": has free slots. room size: " + this.players.length);
                return true;
            }
        }
        System.err.println(this.toString() + ": slots are full");
        return false;
    }

    public void gameOver() {
        winner(score);
    }

    public void checkWord(String word, PlayerHandler p) {

        StringInputScanner ask = new StringInputScanner();
        ask.setMessage(GFXGenerator.drawRope(score, players[0].getTeam(), players[1].getTeam()) + word + " = ");

        if (p.getPrompt().getUserInput(ask).equals(word)) {
            score += p.getTeam().getValue();
        }

    }

    public void checkEquation(String[] numbers, PlayerHandler p) {

        StringInputScanner ask = new StringInputScanner();
        ask.setMessage(GFXGenerator.drawRope(score, players[0].getTeam(), players[1].getTeam()) + numbers[0] + " = ");

        if (p.getPrompt().getUserInput(ask).equals(numbers[1])) {
            score += p.getTeam().getValue();
        }

    }

    public void updateScore(PlayerHandler player) {
        score += player.getTeam().getValue();
    }


    public void winner(int score) {
        switch (score) {
            case 0:
                announceWinner(teams[0]);
                break;
            case 100:
                announceWinner(teams[1]);
                break;
        }
    }

    public void announceWinner(Team team) {

        for (PlayerHandler p : players) {
            if(p.getTeam() == team) {
                p.getOutputStream().println(
                        GFXGenerator.drawYouWon(p.getTeam().getColor(), score, teams[0], teams[1]));
                continue;
            }
            p.getOutputStream().println(
                    GFXGenerator.drawGameOver(p.getTeam().getColor(), score, teams[0], teams[1]));
        }


    }

    @Override
    public String toString() {
        return gameType.toString();
    }

    public GameType getGameType() {
        return gameType;
    }

    public int getNumMaxPlayers() {
        return numMaxPlayers;
    }

    public int getActivePlayers() {
        return activePlayers;
    }

    public synchronized int getScore() {
        return score;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public PlayerHandler[] getPlayers() {
        return players;
    }
}