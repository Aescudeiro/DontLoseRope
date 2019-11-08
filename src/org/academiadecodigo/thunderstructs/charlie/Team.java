package org.academiadecodigo.thunderstructs.charlie;

import org.academiadecodigo.thunderstructs.charlie.Utilities.Color;

public enum Team {

    RED("Red", 10, Color.ANSI_RED),
    BLUE("Blue", -10, Color.ANSI_BLUE);

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