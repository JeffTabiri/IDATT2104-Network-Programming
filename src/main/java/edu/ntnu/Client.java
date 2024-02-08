package edu.ntnu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    final int PORTNR = 1250;
    Socket connection;

    BufferedReader reader;

    PrintWriter writer;

    Scanner readFromCommand;



    public Client() throws IOException {
        readFromCommand = new Scanner(System.in);
        System.out.print("What is the name of the server?");
        String server = readFromCommand.nextLine();
        connection = new Socket(server, PORTNR);
        System.out.println("Connection successful.");
    }

    public void openStreams() throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
        reader = new BufferedReader(inputStreamReader);
        writer = new PrintWriter(connection.getOutputStream(), true);

        String text1 = reader.readLine();
        System.out.println(text1);
    }

    public void getData() throws Exception {

        String client = "A";

        while(!client.isEmpty()) {
            writer.println(client);
            String response = reader.readLine();
            System.out.println("From server: " + response);
            client = readFromCommand.nextLine();
        }

    }




    public void closeStreams() throws Exception{
        reader.close();
        writer.close();
        connection.close();
    }


}
