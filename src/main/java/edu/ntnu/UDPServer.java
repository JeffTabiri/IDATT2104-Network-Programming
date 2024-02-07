package edu.ntnu;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class UDPServer extends Thread {

    private DatagramSocket socket;

    private boolean running;

    private byte[] buffer = new byte[256];

    public UDPServer() throws Exception {
        socket = new DatagramSocket(8080);
    }

    public void run() {
        running = true;

        while(running) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length); //Receive incoming messages.


            try {
                socket.receive(packet); //Blocks until a message arrives and stores message inside.
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //Retrieve address of the incoming message.
            InetAddress address = packet.getAddress();
            int port = packet.getPort();



            String received = new String(packet.getData(), 0, packet.getLength());

            String result = evaluateExpression(received);

            byte[] resultData = result.getBytes(StandardCharsets.UTF_8);

            //Create a datagram packet for sending a message to client.
            packet = new DatagramPacket(resultData, resultData.length, address, port);



            System.out.println(received);

            if (received.contains("end")) {
                running = false;
                continue;
            }

            try {
                socket.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        socket.close();
    }

    private String evaluateExpression(String expression) {
        try {
            //Split the expression into operand and operator.
            String[] tokens = expression.split("\\s+");
            double firstOperand = Double.parseDouble(tokens[0]);
            double secondOperand = Double.parseDouble(tokens[2]);
            char operator = tokens[1].charAt(0);

            double result;
            switch (operator) {
                case '+':
                    result = firstOperand + secondOperand;
                    break;
                case '-':
                    result = firstOperand - secondOperand;
                    break;

                default:
                    return "Error in evaluating expression.";
            }
            return String.valueOf(result);
        } catch (Exception e) {
            return "Error in evaluating expression.";
        }
    }

}
