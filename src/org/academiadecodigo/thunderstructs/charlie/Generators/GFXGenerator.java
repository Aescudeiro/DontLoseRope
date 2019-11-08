package org.academiadecodigo.thunderstructs.charlie.Generators;

public class GFXGenerator {

    public static void drawRope(int score){


        String firstRope = "";
        String secondRope = "";
        int score2 = (50 - score) + 50;

        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        for(int i = 0; i < score; i++){
            sb.append("~");
        }

        for(int i = 0; i < score2; i++){
            sb2.append("~");
        }

        String s5 = "#################################################################################################################################";
        String s6 = "#                                                                                                                               #";
        String s7 = "#   BLUE TEAM " + sb.toString() + "||" + sb2.toString() + " RED TEAM   #";
        String s8 = "#                                                                                                                               #";
        String s9 = "#################################################################################################################################";

        System.out.println(s5);
        System.out.println(s6);
        System.out.println(s7);
        System.out.println(s8);
        System.out.println(s9);

    }

    public static void drawGameTitle(){

        StringBuilder sb = new StringBuilder();

                sb.append("\n'  8888888b.                   d8b 888          888                                       8888888b.                                           \n");
                sb.append("'  888  \"Y88b                  88P 888          888                                       888   Y88b                                         \n");
                sb.append("'  888    888                  8P  888          888                                       888    888                                          \n");
                sb.append("'  888    888  .d88b.  88888b. \"   888888       888       .d88b.  .d8888b   .d88b.        888   d88P  .d88b.  88888b.   .d88b.               \n");
                sb.append("'  888    888 d88\"\"88b 888 \"88b    888          888      d88\"\"88b 88K      d8P  Y8b       8888888P\"  d88\"\"88b 888 \"88b d8P  Y8b      \n");
                sb.append("'  888    888 888  888 888  888    888          888      888  888 \"Y8888b. 88888888       888 T88b   888  888 888  888 88888888              \n");
                sb.append("'  888  .d88P Y88..88P 888  888    Y88b.        888      Y88..88P      X88 Y8b.           888  T88b  Y88..88P 888 d88P Y8b.                   \n");
                sb.append("'  8888888P\"   \"Y88P\"  888  888     \"Y888       88888888  \"Y88P\"   88888P'  \"Y8888        888   T88b  \"Y88P\"  88888P\"   \"Y8888     \n");
                sb.append("'                                                                                                             888                             \n");
                sb.append("'                                                                                                             888                             \n");
                sb.append("'                                                                                                             888                               ");

        System.err.println(sb.toString());

    }

    public static void main(String[] args) {
        drawGameTitle();
        drawRope(50);
    }

}