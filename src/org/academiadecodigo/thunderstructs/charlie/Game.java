package org.academiadecodigo.thunderstructs.charlie;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import org.academiadecodigo.thunderstructs.charlie.Generators.ChallengeGenerator;
import org.academiadecodigo.thunderstructs.charlie.Utilities.Messages;

import java.io.IOException;

public class Game {

    private int score;
    private int numMaxPlayers;
    private int difficulty;

    private PlayerHandler[] players;
    private Prompt prompt;
    private GameType gameType;


    public Game(int numMaxPlayers, GameType type, int difficulty) {

        score = 50;

        this.numMaxPlayers = numMaxPlayers;
        gameType = type;
        this.difficulty = difficulty;

        players = new PlayerHandler[numMaxPlayers];

    }

    public void init() {

        System.out.println("entered game init");

        while (score >= 0 || score <= 100) {

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

        for (int i = 0; i < numMaxPlayers; i++) {

            System.out.println("adding player");
            if (players[i] == null) {
                players[i] = player;
                break;
            }
        }
    }

    public boolean hasEmptySlots() {

        System.out.println("enter empty slots");

        for (PlayerHandler p : players) {
            //System.out.println(p.getName());
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

    public void checkWord(String word, PlayerHandler p) {

        StringInputScanner ask = new StringInputScanner();
        ask.setMessage(word);

        if(p.getPrompt().getUserInput(ask).equals(word)) {
            score += p.getTeam().getValue();
        }

    }

    public void checkEquation(String[] numbers, PlayerHandler p) {

        StringInputScanner ask = new StringInputScanner();
        ask.setMessage(getEquation(numbers));

        if(p.getPrompt().getUserInput(ask).equals(getNumber(numbers))){
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


    public static void main(String[] args) {



    }

}