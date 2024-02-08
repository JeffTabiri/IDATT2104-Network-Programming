package edu.ntnu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread implements Runnable {

    private Socket connection;
    private BufferedReader reader;
    private PrintWriter writer;

    /**
     * Creates a server object and waits for client to connect.
     */
    public ServerThread(Socket socket) throws Exception {
        this.connection = socket;
    }

    public void openStreams() throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
        reader = new BufferedReader(inputStreamReader);
        writer = new PrintWriter(connection.getOutputStream(), true);
        writer.println("You have contacted server: " + connection.getPort() + ".");
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


    public void run() {
        try {
            openStreams();
            getData();
            closeStreams();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
