package org.academiadecodigo.thunderstructs.charlie;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import org.academiadecodigo.thunderstructs.charlie.Generators.ChallengeGenerator;
import org.academiadecodigo.thunderstructs.charlie.Generators.GFXGenerator;
import org.academiadecodigo.thunderstructs.charlie.Utilities.Messages;

import java.io.IOException;

public class Game {

    private int score;
    private int numMaxPlayers;
    private int difficulty;
    private int activePlayers;

    private PlayerHandler[] players;
    private Prompt prompt;
    private GameType gameType;


    public Game(int numMaxPlayers, GameType type, int difficulty) {

        score = 50;
        this.activePlayers = 0;

        this.numMaxPlayers = numMaxPlayers;
        gameType = type;
        this.difficulty = difficulty;

        players = new PlayerHandler[numMaxPlayers];

    }

    public void init() {

        System.out.println(players[activePlayers].getName() + " is waiting for game " + this.toString() + " to start...");

        while ((score >= 0 || score <= 100) && (activePlayers == numMaxPlayers - 1)) {

            for(PlayerHandler p : players) {

                switch(gameType) {
                    case CALC:
                        checkEquation(ChallengeGenerator.generateEquation(difficulty), p);
                        break;

                    case WORDS:
                        checkWord(ChallengeGenerator.generateWord(difficulty), p);
                        break;
                }

            }

        }

        winner(score);
    }

    public void addPlayer(PlayerHandler player) {

        if(activePlayers < numMaxPlayers){
            players[activePlayers] = player;
            System.out.println(player.getName() + " added to game as player " + (activePlayers + 1));
            activePlayers++;
            return;
        }
        System.out.println("This room (" + this.toString() + ") is full");
        /*for (int i = 0; i < numMaxPlayers; i++) {

            System.out.println("adding player");
            if (players[i] == null) {
                players[i] = player;
                break;
            }
        }*/
    }

    public boolean hasEmptySlots() {

        for (PlayerHandler p : players) {
            if(p == null) {
                System.err.println(this.toString() + ": has free slots");
                return true;
            }
        }
        System.err.println(this.toString() + ": slots are full");
        return false;
    }

    public void checkWord(String word, PlayerHandler p) {

        StringInputScanner ask = new StringInputScanner();
        ask.setMessage(GFXGenerator.drawRope(score, players[0].getTeam(), players[1].getTeam()) + word);

        if(p.getPrompt().getUserInput(ask).equals(word)) {
            score += p.getTeam().getValue();
        }

    }

    public void checkEquation(String[] numbers, PlayerHandler p) {

        StringInputScanner ask = new StringInputScanner();
        ask.setMessage(GFXGenerator.drawRope(score, players[0].getTeam(), players[1].getTeam()) + numbers[0]);

        if(p.getPrompt().getUserInput(ask).equals(numbers[1])){
            score += p.getTeam().getValue();
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

        for (PlayerHandler p : players) {
            try {
                p.getOutputStream().println(Messages.WINNER_ANNOUNCEMENT + " " + team.getTeam());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public String toString() {
        return gameType.toString();
    }
}