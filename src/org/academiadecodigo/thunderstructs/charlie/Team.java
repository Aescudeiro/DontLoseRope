package org.academiadecodigo.thunderstructs.charlie;

public enum Team {

    RED("Red", 10),
    BLUE("Blue", -10);

    private String team;
    private int value;

    Team(String team, int value) {

        this.team = team;
        this.value = value;

    }

    public String getTeam() {
        return team;
    }

    public int getValue() {
        return value;
    }
}