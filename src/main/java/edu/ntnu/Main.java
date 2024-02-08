package edu.ntnu;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        Client client = null;
        try {
            client = new Client();
            client.openStreams();
            client.getData();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            assert client != null;
            client.closeStreams();
        }
    }
}