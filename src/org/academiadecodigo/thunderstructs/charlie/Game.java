package org.academiadecodigo.thunderstructs.charlie;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import org.academiadecodigo.thunderstructs.charlie.Generators.ChallengeGenerator;
import org.academiadecodigo.thunderstructs.charlie.Utilities.Messages;

public class Game {

    private int score;
    private int numMaxPlayers;
    private int difficulty;

    private Player[] players;
    private Prompt prompt;
    private GameType gameType;


    public Game(int numMaxPlayers, GameType type, int difficulty) {

        score = 50;

        this.numMaxPlayers = numMaxPlayers;
        gameType = type;
        this.difficulty = difficulty;

        players = new Player[numMaxPlayers];

    }

    public void init() {

        while (score >= 0 || score <= 100) {

            for(Player p : players) {

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

    public String getNumber(String[] array) {
        return array[1];
    }

    public String getEquation(String[] array){
        return array[0];
    }

    public void checkWord(String word, Player p) {

        StringInputScanner ask = new StringInputScanner();
        ask.setMessage(word);

        if(prompt.getUserInput(ask).equals(word)) {
            score += p.getTeam().getValue();
        }

    }

    public void checkEquation(String[] numbers, Player p) {

        StringInputScanner ask = new StringInputScanner();
        ask.setMessage(getEquation(numbers));

        if(prompt.getUserInput(ask).equals(getNumber(numbers)){
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

        for (Player p : players) {
            p.getOutputStream().println(Messages.WINNER_ANNOUNCEMENT + " " + team.getTeam());
        }

    }


    public static void main(String[] args) {



    }

}