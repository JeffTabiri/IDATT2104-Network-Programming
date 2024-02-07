package edu.ntnu;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UDPClient {
    private DatagramSocket socket;
    private InetAddress address; //Address of our server.
    private byte[] buffer;
    Scanner commandLine;


    public UDPClient() throws Exception {
        commandLine = new Scanner(System.in);
        this.socket = new DatagramSocket();
        this.address = InetAddress.getByName("localhost");
    }

    public void getInput() throws IOException {

        String messageToBeSent;

        do {
            messageToBeSent = commandLine.nextLine();
            System.out.println(sendMessage(messageToBeSent));
        } while (!messageToBeSent.isEmpty());

        System.out.println("Closing");
    }


    /**
     * Sends message to our server.
     * @param msg is the information we would want to send to the server.
     * @return a message from the server.
     */
    public String sendMessage(String msg) throws IOException {
        buffer  = msg.getBytes(StandardCharsets.UTF_8);

        //Creates data packet to send to server.
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 8080);
        socket.send(packet);

        //Creates a packet ti be received from server.
        packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength());


        return received;
    }


    public void close() {
        socket.close();
    }

}
