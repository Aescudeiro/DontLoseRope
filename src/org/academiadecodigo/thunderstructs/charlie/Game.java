package org.academiadecodigo.thunderstructs.charlie;

import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import org.academiadecodigo.thunderstructs.charlie.Generators.GFXGenerator;

import java.util.Arrays;


public class Game {

    private static final int MAX_SCORE = 100;
    private volatile int score;
    private volatile int activePlayers;
    private int numMaxPlayers;
    private int difficulty;
    private boolean fixedGame;

    private Team[] teams;
    private PlayerHandler[] players;
    private GameType gameType;


    public Game(int numMaxPlayers, GameType type, int difficulty, Team team1, Team team2, boolean fixed) {

        fixedGame = fixed;
        score = MAX_SCORE / 2;
        activePlayers = 0;

        teams = new Team[2];
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

        for (PlayerHandler playerHandler : players) {
            if (playerHandler == null) {
                System.err.println(this.toString() + ": has free slots. room size: " + this.players.length);
                return true;
            }
        }
        System.err.println(this.toString() + ": slots are full");
        return false;
    }

    public void gameOver(PlayerHandler playerHandler) {

        winner(score);

        if (!fixedGame) {
            for (Game game : Server.getGames().values()) {
                if (game.equals(this)) {
                    Server.getGames().remove(playerHandler.getGameRoom());
                    return;
                }
            }
            System.out.println("removed game");
        }

        Arrays.fill(players, null);
        //gameFinished = true;
    }

    public void checkWord(String word, PlayerHandler playerHandler) {

        StringInputScanner ask = new StringInputScanner();
        ask.setMessage(GFXGenerator.drawRope(score, teams[0], teams[1]) + word + " = ");

        if (playerHandler.getPrompt().getUserInput(ask).equals(word)) {
            updateScore(playerHandler);
        }

    }

    public void checkEquation(String[] numbers, PlayerHandler playerHandler) {

        StringInputScanner ask = new StringInputScanner();
        ask.setMessage(GFXGenerator.drawRope(score, teams[0], teams[1]) + numbers[0] + " = ");

        if (playerHandler.getPrompt().getUserInput(ask).equals(numbers[1])) {
            //score += playerHandler.getTeam().getValue();
            updateScore(playerHandler);
        }
    }

    public void updateScore(PlayerHandler playerHandler) {
        if(playerHandler.getTeam() == teams[0]) {
            score -= Server.TEAM_SCORE;

            return;
        }
        score += Server.TEAM_SCORE;
    }

    public void winner(int score) {
        // TODO: 08/11/2019 if statement. so it can have different value points thing cant have 15 as points

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

        for (PlayerHandler playerHandler : players) {

            if (playerHandler.getTeam() == team) {
                playerHandler.getOutputStream().println(
                        GFXGenerator.drawYouWon(playerHandler.getTeam().getColor(), score, teams[0], teams[1]));
                continue;
            }

            playerHandler.getOutputStream().println(
                    GFXGenerator.drawGameOver(playerHandler.getTeam().getColor(), score, teams[0], teams[1]));
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

    public Team[] getTeams() {
        return teams;
    }
}