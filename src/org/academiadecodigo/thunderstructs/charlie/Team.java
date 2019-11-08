package org.academiadecodigo.thunderstructs.charlie;

import org.academiadecodigo.thunderstructs.charlie.Utilities.Color;

public enum Team {

    RED("Red", Server.TEAM_SCORE, Color.ANSI_RED),
    BLUE("Blue", Server.TEAM_SCORE, Color.ANSI_BLUE),
    GREEN("Green", Server.TEAM_SCORE, Color.ANSI_GREEN),
    YELLOW("Yellow", Server.TEAM_SCORE, Color.ANSI_YELLOW);

    private String team;
    private int value;
    private String color;

    Team(String team, int value, String color) {

        this.team = team;
        this.value = value;
        this.color = color;

    }

    public String getTeam() {
        return team;
    }

    public int getValue() {
        return value;
    }

    public String getColor() {
        return color;
    }
}