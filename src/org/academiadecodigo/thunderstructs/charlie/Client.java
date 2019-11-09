package org.academiadecodigo.thunderstructs.charlie;

import org.academiadecodigo.thunderstructs.charlie.Utilities.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public Client(String[] args) {

        try {
            socket = new Socket(args[0], Integer.parseInt(args[1]));
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {

            e.printStackTrace();

        }
    }


    public void chat() {


        Scanner input = new Scanner(System.in);

        Thread readMessage = new Thread(new Runnable() {
            @Override
            public void run() {

                String msg;
                while (true) {

                    try {

                        // read the message sent to this client
                        while ((msg = in.readLine()) != null) {
                            System.out.println(msg + " ");
                            if(msg.contains(Color.ANSI_BLUE + "Thanks " + Color.ANSI_RED + "for " + Color.ANSI_GREEN +
                                    "playing!")) {
                                in.close();
                                out.close();
                                System.exit(0);
                            }
                        }
                    } catch (IOException e) {

                        System.err.println("Connection terminated");
                        break;
                    }
                }
            }
        });

        readMessage.start();

        // sendMessage thread
        while (true) {

            String msg = input.nextLine();
            out.println(msg);

        }

    }

    public static void main(String[] args) {

        Client client = new Client(args);
        client.chat();
    }
}
