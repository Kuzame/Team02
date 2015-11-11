package FanManager.network;

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import circle.Circle;

public class Server extends JFrame {

    // Text area for displaying contents

    private JTextArea jta = new JTextArea();

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        // Place text area on the frame
        setLayout(new BorderLayout());
        add(new JScrollPane(jta), BorderLayout.CENTER);

        setTitle("Server");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true); // It is necessary to show the frame here!

        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(7000);
            jta.append("Server started at " + new Date() + '\n');

            // Listen for a connection request
            Socket socket = serverSocket.accept();

            // Create data input and output streams
            ObjectOutputStream outputToClient = new ObjectOutputStream(
                    socket.getOutputStream());
            outputToClient.flush();
            ObjectInputStream inputFromClient = new ObjectInputStream(
                    socket.getInputStream());

            while (true) {
                // Receive radius from the client
                Circle circle = (Circle) inputFromClient.readObject();
                double radius = circle.getRadius();

                // Compute area
                double area = radius * radius * Math.PI;

                // Send area back to the client
                outputToClient.writeDouble(area);
                outputToClient.flush();

                jta.append("Radius received from client: " + radius + '\n');
                jta.append("Area found: " + area + '\n');
            }
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (ClassNotFoundException ex) {
            System.err.println(ex);
        }
    }
}
