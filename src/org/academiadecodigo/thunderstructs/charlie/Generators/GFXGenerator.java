package org.academiadecodigo.thunderstructs.charlie.Generators;

import org.academiadecodigo.thunderstructs.charlie.Team;
import org.academiadecodigo.thunderstructs.charlie.Utilities.Color;

public class GFXGenerator {

    private static final String CLEAR_SPACE = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
            "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";

    public static String clearScreen(){
        return CLEAR_SPACE;
    }

    public static String drawRope(int score, Team t1, Team t2){

        int score2 = (50 - score) + 50;

        StringBuilder firstRope = new StringBuilder();
        StringBuilder secondRope = new StringBuilder();
        String team1 = t1.toString() + " TEAM ";
        String team2 = " " + t2.toString() + " TEAM";

        team1 = setTeamSpacing(team1) + team1;
        team2 = team2 + setTeamSpacing(team2);

        for(int i = 0; i < score; i++){
            firstRope.append("~");
        }

        for(int i = 0; i < score2; i++){
            secondRope.append("~");
        }

        String s5 = " ########################################################################################################################################\n";
        String s6 = " #                                                                                                                                      #\n";
        String s7 = " #" + t1.getColor() + team1 + Color.ANSI_RESET + t2.getColor() + firstRope.toString() + Color.ANSI_RESET + "||" + t1.getColor() + secondRope.toString() + Color.ANSI_RESET + t2.getColor() + team2 + Color.ANSI_RESET + "#\n";
        String s8 = " #                                                                                                                                      #\n";
        String s9 = " ########################################################################################################################################\n";

        return CLEAR_SPACE + drawGameTitle() + s5 + s6 + s7 + s8 + s9;

    }

    private static String setTeamSpacing(String team){

        String spacing = "";
        return spacing.repeat(16 - team.length());

    }

    public static String drawGameTitle(){

        StringBuilder sb = new StringBuilder();

                sb.append(Color.ANSI_BLUE + "      8888888b.                   d8b 888     " + Color.ANSI_RESET + Color.ANSI_RED + "     888                                    " + Color.ANSI_RESET + Color.ANSI_GREEN + "   8888888b.                                           \n" + Color.ANSI_RESET);
                sb.append(Color.ANSI_BLUE + "      888  \"Y88b                  88P 888      " + Color.ANSI_RESET + Color.ANSI_RED + "    888                                     " + Color.ANSI_RESET + Color.ANSI_GREEN + "  888   Y88b                                         \n" + Color.ANSI_RESET);
                sb.append(Color.ANSI_BLUE + "      888    888                  8P  888       " + Color.ANSI_RESET + Color.ANSI_RED + "   888                                      " + Color.ANSI_RESET + Color.ANSI_GREEN + " 888    888                                          \n" + Color.ANSI_RESET);
                sb.append(Color.ANSI_BLUE + "      888    888  .d88b.  88888b. \"   888888   " + Color.ANSI_RESET + Color.ANSI_RED + "    888       .d88b.  .d8888b   .d88b.      " + Color.ANSI_RESET + Color.ANSI_GREEN + "  888   d88P  .d88b.  88888b.   .d88b.               \n" + Color.ANSI_RESET);
                sb.append(Color.ANSI_BLUE + "      888    888 d88\"\"88b 888 \"88b    888    " + Color.ANSI_RESET + Color.ANSI_RED + "      888      d88\"\"88b 88K      d8P  Y8b " + Color.ANSI_RESET + Color.ANSI_GREEN + "      8888888P\"  d88\"\"88b 888 \"88b d8P  Y8b      \n" + Color.ANSI_RESET);
                sb.append(Color.ANSI_BLUE + "      888    888 888  888 888  888    888       " + Color.ANSI_RESET + Color.ANSI_RED + "   888      888  888 \"Y8888b. 88888888     " + Color.ANSI_RESET + Color.ANSI_GREEN + "  888 T88b   888  888 888  888 88888888              \n" + Color.ANSI_RESET);
                sb.append(Color.ANSI_BLUE + "      888  .d88P Y88..88P 888  888    Y88b.     " + Color.ANSI_RESET + Color.ANSI_RED + "   888      Y88..88P      X88 Y8b.          " + Color.ANSI_RESET + Color.ANSI_GREEN + " 888  T88b  Y88..88P 888 d88P Y8b.                   \n" + Color.ANSI_RESET);
                sb.append(Color.ANSI_BLUE + "      8888888P\"   \"Y88P\"  888  888     \"Y888" + Color.ANSI_RESET + Color.ANSI_RED + "       88888888  \"Y88P\"   88888P'  \"Y8888" + Color.ANSI_RESET + Color.ANSI_GREEN + "        888   T88b  \"Y88P\"  88888P\"   \"Y8888     \n" + Color.ANSI_RESET);
                sb.append(Color.ANSI_BLUE + "                                                " + Color.ANSI_RESET + Color.ANSI_RED + "                                            " + Color.ANSI_RESET + Color.ANSI_GREEN + "                     888                             \n" + Color.ANSI_RESET);
                sb.append(Color.ANSI_BLUE + "                                                " + Color.ANSI_RESET + Color.ANSI_RED + "                                            " + Color.ANSI_RESET + Color.ANSI_GREEN + "                     888                             \n" + Color.ANSI_RESET);
                sb.append(Color.ANSI_BLUE + "                                                " + Color.ANSI_RESET + Color.ANSI_RED + "                                            " + Color.ANSI_RESET + Color.ANSI_GREEN + "                     888                             \n" + Color.ANSI_RESET);

        return sb.toString();

    }

    public static String drawGameOver(String c, int score, Team t1, Team t2){

        StringBuilder sb = new StringBuilder();

        String color = c;

            sb.append(color + "                  .d8888b.         d8888 888b     d888 8888888888       .d88888b.  888     888 8888888888 8888888b.\n");
            sb.append(color + "                  d88P  Y88b       d88888 8888b   d8888 888             d88P\" \"Y88b 888     888 888        888   Y88b\n");
            sb.append(color + "                  888    888      d88P888 88888b.d88888 888             888     888 888     888 888        888    888\n");
            sb.append(color + "                  888            d88P 888 888Y88888P888 8888888         888     888 Y88b   d88P 8888888    888   d88P\n");
            sb.append(color + "                  888  88888    d88P  888 888 Y888P 888 888             888     888  Y88b d88P  888        8888888P\"\n");
            sb.append(color + "                  888    888   d88P   888 888  Y8P  888 888             888     888   Y88o88P   888        888 T88b\n");
            sb.append(color + "                  Y88b  d88P  d8888888888 888   \"   888 888             Y88b. .d88P    Y888P    888        888  T88b\n");
            sb.append(color + "                  \"Y8888P88 d88P     888 888       888 8888888888       \"Y88888P\"      Y8P     8888888888 888   T88b\n");

        return CLEAR_SPACE + drawRope(score, t1, t2) + "\n\n" + sb.toString() + Color.ANSI_RESET;
    }

    public static String drawYouWon(String c, int score, Team t1, Team t2){

        String color = c;

        StringBuilder sb = new StringBuilder();

            sb.append(color + "                              Y88b   d88P  .d88888b.  888     888      888       888  .d88888b.  888b    888\n");
            sb.append(color + "                               Y88b d88P  d88P\" \"Y88b 888     888      888   o   888 d88P\" \"Y88b 8888b   888\n");
            sb.append(color + "                                Y88o88P   888     888 888     888      888  d8b  888 888     888 88888b  888\n");
            sb.append(color + "                                 Y888P    888     888 888     888      888 d888b 888 888     888 888Y88b 888\n");
            sb.append(color + "                                  888     888     888 888     888      888d88888b888 888     888 888 Y88b888\n");
            sb.append(color + "                                  888     888     888 888     888      88888P Y88888 888     888 888  Y88888\n");
            sb.append(color + "                                  888     Y88b. .d88P Y88b. .d88P      8888P   Y8888 Y88b. .d88P 888   Y8888\n");
            sb.append(color + "                                  888      \"Y88888P\"   \"Y88888P\"       888P     Y888  \"Y88888P\"  888    Y888\n");

        return CLEAR_SPACE + drawRope(score, t1, t2) + "\n\n" + sb.toString() + Color.ANSI_RESET;

    }

    public static String showGameInstructions(){
        return null;
    }

    public static String drawCountDown(int count){
        return null;
    }

}