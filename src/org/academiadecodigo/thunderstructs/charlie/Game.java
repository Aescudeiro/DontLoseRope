package org.academiadecodigo.thunderstructs.charlie;

import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import org.academiadecodigo.thunderstructs.charlie.Generators.GFXGenerator;

import java.util.Arrays;

/**
 * This class represents the different game rooms.
 * Pre-created game rooms are fixed and reseted after the match is over.
 * Custom game rooms (created by users) are not fixed, and will be deleted after the match is over.
 */
public class Game {

    private static final int MAX_SCORE = 100;
    private volatile int score;
    private volatile int activePlayers;
    private int numMaxPlayers;
    private int difficulty;
    private boolean fixedGame;

    private String name;

    private Team[] teams;
    private PlayerHandler[] players;
    private GameType gameType;


    public Game(String name, int numMaxPlayers, GameType type, int difficulty, Team team1, Team team2, boolean fixed) {

        fixedGame = fixed;
        score = MAX_SCORE / 2;
        activePlayers = 0;

        teams = new Team[2];
        teams[0] = team1;
        teams[1] = team2;

        this.name = name;
        this.numMaxPlayers = numMaxPlayers;
        gameType = type;
        this.difficulty = difficulty;

        players = new PlayerHandler[numMaxPlayers];

    }

    /**
     * Add player to the players[]
     *
     * @param player Player reference to be added
     */
    public synchronized void addPlayer(PlayerHandler player) {

        if (activePlayers < numMaxPlayers) {

            players[activePlayers] = player;
            System.out.println(player.getName() + " added to game as player " + (activePlayers + 1));
            activePlayers++;
            return;
        }

        System.out.println("This room (" + this.toString() + ") is full");
    }

    /**
     * Check if game room has empty slots
     *
     * @return true if has game has PlayerHandler[] is not filled (has empty slots)
     */
    //TODO: Refactor name
    public boolean hasEmptySlots() {
        return (numMaxPlayers - activePlayers) != 0;
    }

    /**
     * Check if there is a team with no elements
     * If, at the last player, one of the teams doesn't have players, this player will be forced to join that team
     *
     * @return an array of available teams to be joined by the player, last index is null allowing for another menu option
     */
    public String[] getAvailableTeams() {

        int team1 = 0;
        int team2 = 0;
        if(activePlayers > 0){
            for(int i = 0; i < activePlayers; i++) {
                if(players[i].getTeam() == teams[0]){
                    team1++;
                    continue;
                }
                team2++;
            }

            String[] team = new String[2];
            if((activePlayers == numMaxPlayers-1) && (team1 == 0 || team2 == 0)) {
                if(team1 == 0) {
                    team[0] = teams[0].toString();
                    return team;
                }
                team[0] = teams[1].toString();
                return team;
            }
        }
        return new String[]{teams[0].toString(), teams[1].toString(), null};
    }

    /**
     * End game screen and game room resetter:
     *
     * If game is fixed, resets game room;
     * If game is not fixed, deletes room from Server's games HashMap;
     *
     * @param playerHandler Player reference that scored the final points
     */
    //TODO: Refactor name
    public void gameOver(PlayerHandler playerHandler) {

        winner();

        if (!fixedGame) {
            for (Game game : Server.getGames().values()) {
                if (game.equals(this)) {
                    Server.getGames().remove(playerHandler.getGameRoom());
                    return;
                }
            }
            System.out.println("removed game");
        }

        resetGameRoom();

    }

    /**
     * Check if player answer (word) is correct
     *
     * @param word correct answer to the challenge (to be compared to player answer)
     * @param playerHandler reference of the player that answered
     */
    public void checkWord(String word, PlayerHandler playerHandler) {

        StringInputScanner ask = new StringInputScanner();
        ask.setMessage(GFXGenerator.drawRope(score, teams[0], teams[1]) + word + " = ?? \n");

        if (playerHandler.getPrompt().getUserInput(ask).equals(word)) {
            updateScore(playerHandler);
        }
    }

    /**
     * Check if player answer (number) is correct
     *
     * @param numbers String[] that contains the equation (numbers[0]) and the answer (numbers[1])
     * @param playerHandler reference of the player that answered
     */
    public void checkEquation(String[] numbers, PlayerHandler playerHandler) {

        StringInputScanner ask = new StringInputScanner();
        ask.setMessage(GFXGenerator.drawRope(score, teams[0], teams[1]) + numbers[0] + " = ?? \n");

        if (playerHandler.getPrompt().getUserInput(ask).equals(numbers[1])) {
            //score += playerHandler.getTeam().getValue();
            updateScore(playerHandler);
        }
    }

    /**
     * Updates the score if the player answer is correct
     *
     * @param playerHandler reference of the player that answered
     */
    public synchronized void updateScore(PlayerHandler playerHandler) {

        int points = teams.length * Server.TEAM_SCORE / numMaxPlayers;

        if (playerHandler.getTeam() == teams[0]) {
            score -= points;
            System.out.println(score);
            return;
        }
        score += points;
        System.out.println(score);
    }

    /**
     * Check winner team
     */

    public void winner() {

        if (score <= 0) {
            announceWinner(teams[0]);
            return;
        }

        announceWinner(teams[1]);
    }

    /**
     * Broadcast final message:
     *
     * You won for the winning team;
     * Game Over for the losing team;
     *
     * @param team reference for the winning team
     */
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

    /**
     * Resets actual room (if the game instance is fixed)
     */
    public void resetGameRoom() {
        score = MAX_SCORE / 2;
        activePlayers = 0;
        Arrays.fill(players, null);
    }


    @Override
    public String toString() {
        return gameType.toString();
    }

    /**
     * Getters & Setters
     */

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

    public String getName() {
        return name;
    }

    public void setNumMaxPlayers(int numMaxPlayers) {
        this.numMaxPlayers = numMaxPlayers;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public void setTeam1(Team team) {
        teams[0] = team;
    }

    public void setTeam2(Team team) {
        teams[1] = team;
    }
}