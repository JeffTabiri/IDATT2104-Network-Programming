package edu.ntnu;

public class Main {
    public static void main(String[] args) throws Exception {
        UDPServer server = new UDPServer();
        server.start();

        UDPClient client = new UDPClient();

        client.getInput();

    }
}