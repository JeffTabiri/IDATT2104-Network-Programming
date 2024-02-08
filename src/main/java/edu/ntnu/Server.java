package edu.ntnu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;



public class Server {
    private final int PORTNR = 1250;
    private ServerSocket server;
    private Socket connection;
    private BufferedReader reader;
    private PrintWriter writer;


    /**
     * Creates a server object and waits for client to connect.
     */
    public Server() throws Exception {
        this.server = new ServerSocket(PORTNR);
        server.setReuseAddress(true);

        while (true) {
            System.out.println("Starting server.");
            System.out.println("Waiting for client.");
            this.connection = server.accept();
            openStreams();
            getData();

            ServerThread thread = new ServerThread(connection);
            System.out.println("Client Connected.");
            new Thread(thread).start();
        }

    }



    public void openStreams() throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
        reader = new BufferedReader(inputStreamReader);
        writer = new PrintWriter(connection.getOutputStream(), true);
        writer.println("You have contacted server: " + PORTNR + ".");
    }

    public void getData() throws Exception{



        String fromClient = reader.readLine();

        while(fromClient != null) {
            writer.println("Write a number.");
            String firstNumber = reader.readLine();

            writer.println("Write a second number.");
            String secondNumber = reader.readLine();

            writer.println("Write 1 to add, everything else to subtract.");
            String operator = reader.readLine();

            double response;

            try {
                if (Integer.parseInt(operator) == 1) {
                    response = Double.parseDouble(firstNumber) + Double.parseDouble(secondNumber);
                } else {
                    response = Double.parseDouble(firstNumber) - Double.parseDouble(secondNumber);
                }

                // Send the response back to the client
                writer.println("Result: " + response);
            } catch (Exception exception) {
                writer.println("Input was wrong from user. Has to be numerical.");
            }

            // Read the next input from the client
            fromClient = reader.readLine();
        }

        closeStreams();
    }

    public void closeStreams() throws Exception{
        reader.close();
        writer.close();
        connection.close();
    }

}
