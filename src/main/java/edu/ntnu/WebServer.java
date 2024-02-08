package edu.ntnu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    static final int PORT = 1250;
    private ServerSocket server;
    private Socket connection;
    private BufferedReader reader;
    private PrintWriter writer;


    /**
     * Creates a server object and waits for client to connect.
     */
    public WebServer() throws Exception {

        System.out.println("Waiting for client.");
        this.server = new ServerSocket(PORT);
        this.connection = server.accept();
        System.out.println("CLIENT CONNECTED.");

        openStreams();
        getData();
        closeStreams();
    }



    public void openStreams() throws Exception {
        System.out.println("Open stream");
        InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
        reader = new BufferedReader(inputStreamReader);
        writer = new PrintWriter(connection.getOutputStream(), true);
        writer.println("You have contacted server: " + PORT + ".");
    }

    public void getData() throws Exception{
        System.out.println("Sending data");
        StringBuilder header = new StringBuilder();
        String line;
        while (!(line = reader.readLine()).isEmpty()) {
            header.append(line).append("\n");
        }
        System.out.println("Header form client: \n" + header);

        String response = "HTTP/1.0 200 OK\n" +
                "Content-Type: text/html; charset=utf-8\n" +
                "\n\n" +
                "<HTML><BODY>" +
                "<H1>Hi. You have connected to my simple web server.</H1>" +
                "Header from client:" +
                "<UL>" +
                formatHeaderAsList(header) +
                "</UL>" +
                "</BODY></HTML>";

        // Send data to the client.
        writer.println(response);
    }

    private static String formatHeaderAsList(StringBuilder header) {
        String[] lines = header.toString().split("\n");
        StringBuilder listItem = new StringBuilder();
        for (String line : lines) {
            listItem.append("<LI>").append(line).append("<LI>");
        }
        return listItem.toString();
    }

    public void closeStreams() throws Exception{
        reader.close();
        writer.close();
        connection.close();
    }

    public static void main(String[] args) throws Exception {
        WebServer webServer = new WebServer();
    }

}
