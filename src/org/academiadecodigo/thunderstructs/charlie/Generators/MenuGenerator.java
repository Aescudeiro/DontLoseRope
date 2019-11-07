package org.academiadecodigo.thunderstructs.charlie.Generators;

import org.academiadecodigo.thunderstructs.charlie.Team;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class MenuGenerator {

    public static String askName(Socket playerSocket) throws IOException {

        Scanner input = new Scanner(playerSocket.getInputStream());

        PrintWriter printWriter = new PrintWriter(playerSocket.getOutputStream(), true);

        printWriter.println("Set your username: ");

        return input.nextLine();

    }

    public static int joinGame() {
        return 0;
    }

    public static Team chooseTeam() {
        return null;
    }

}