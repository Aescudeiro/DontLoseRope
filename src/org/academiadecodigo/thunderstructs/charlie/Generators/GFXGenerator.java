package org.academiadecodigo.thunderstructs.charlie.Generators;

import org.academiadecodigo.thunderstructs.charlie.Team;
import org.academiadecodigo.thunderstructs.charlie.Utilities.Color;

import java.sql.SQLOutput;

public class GFXGenerator {

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
        String s7 = " #" + Color.ANSI_BLUE + team1 + firstRope.toString() + Color.ANSI_RESET + "||" + Color.ANSI_RED + secondRope.toString() + team2 + Color.ANSI_RESET + "#\n";
        String s8 = " #                                                                                                                                      #\n";
        String s9 = " ########################################################################################################################################\n";

        String result = s5 + s6 + s7 + s8 + s9;

        return result;

    }

    private static String setTeamSpacing(String team){
        String spacing = "";
        for(int i = 0; i < (16 - team.length()); i++){
            spacing += " ";
        }
        return spacing;
    }

    public static String drawGameTitle(){

        StringBuilder sb = new StringBuilder();

                sb.append(Color.ANSI_BLUE + "    8888888b.                   d8b 888     " + Color.ANSI_RESET + Color.ANSI_RED + "     888                                    " + Color.ANSI_RESET + Color.ANSI_GREEN + "   8888888b.                                           \n" + Color.ANSI_RESET);
                sb.append(Color.ANSI_BLUE + "    888  \"Y88b                  88P 888      " + Color.ANSI_RESET + Color.ANSI_RED + "    888                                     " + Color.ANSI_RESET + Color.ANSI_GREEN + "  888   Y88b                                         \n" + Color.ANSI_RESET);
                sb.append(Color.ANSI_BLUE + "    888    888                  8P  888       " + Color.ANSI_RESET + Color.ANSI_RED + "   888                                      " + Color.ANSI_RESET + Color.ANSI_GREEN + " 888    888                                          \n" + Color.ANSI_RESET);
                sb.append(Color.ANSI_BLUE + "    888    888  .d88b.  88888b. \"   888888   " + Color.ANSI_RESET + Color.ANSI_RED + "    888       .d88b.  .d8888b   .d88b.      " + Color.ANSI_RESET + Color.ANSI_GREEN + "  888   d88P  .d88b.  88888b.   .d88b.               \n" + Color.ANSI_RESET);
                sb.append(Color.ANSI_BLUE + "    888    888 d88\"\"88b 888 \"88b    888    " + Color.ANSI_RESET + Color.ANSI_RED + "      888      d88\"\"88b 88K      d8P  Y8b " + Color.ANSI_RESET + Color.ANSI_GREEN + "      8888888P\"  d88\"\"88b 888 \"88b d8P  Y8b      \n" + Color.ANSI_RESET);
                sb.append(Color.ANSI_BLUE + "    888    888 888  888 888  888    888       " + Color.ANSI_RESET + Color.ANSI_RED + "   888      888  888 \"Y8888b. 88888888     " + Color.ANSI_RESET + Color.ANSI_GREEN + "  888 T88b   888  888 888  888 88888888              \n" + Color.ANSI_RESET);
                sb.append(Color.ANSI_BLUE + "    888  .d88P Y88..88P 888  888    Y88b.     " + Color.ANSI_RESET + Color.ANSI_RED + "   888      Y88..88P      X88 Y8b.          " + Color.ANSI_RESET + Color.ANSI_GREEN + " 888  T88b  Y88..88P 888 d88P Y8b.                   \n" + Color.ANSI_RESET);
                sb.append(Color.ANSI_BLUE + "    8888888P\"   \"Y88P\"  888  888     \"Y888" + Color.ANSI_RESET + Color.ANSI_RED + "       88888888  \"Y88P\"   88888P'  \"Y8888" + Color.ANSI_RESET + Color.ANSI_GREEN + "        888   T88b  \"Y88P\"  88888P\"   \"Y8888     \n" + Color.ANSI_RESET);
                sb.append(Color.ANSI_BLUE + "                                              " + Color.ANSI_RESET + Color.ANSI_RED + "                                            " + Color.ANSI_RESET + Color.ANSI_GREEN + "                     888                             \n" + Color.ANSI_RESET);
                sb.append(Color.ANSI_BLUE + "                                              " + Color.ANSI_RESET + Color.ANSI_RED + "                                            " + Color.ANSI_RESET + Color.ANSI_GREEN + "                     888                             \n" + Color.ANSI_RESET);
                sb.append(Color.ANSI_BLUE + "                                              " + Color.ANSI_RESET + Color.ANSI_RED + "                                            " + Color.ANSI_RESET + Color.ANSI_GREEN + "                     888                               " + Color.ANSI_RESET);

        return sb.toString();

    }

    public static void main(String[] args) {

        System.out.println(drawGameTitle());
        System.out.println(drawRope(50, Team.BLUE, Team.RED));

    }
}