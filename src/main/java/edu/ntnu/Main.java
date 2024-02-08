package edu.ntnu;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            Server server = new Server();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}