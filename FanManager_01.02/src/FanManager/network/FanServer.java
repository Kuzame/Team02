/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FanManager.network;

/**
 *
 * @author Chad
 */

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import FanManager.model.Fan;
import FanManager.model.FanGroup;

public class FanServer extends JFrame {

    // Text area for displaying contents

    private JTextArea jta = new JTextArea();
    private Socket socket;
    private ServerSocket serverSocket;
    
    public static void main(String[] args) {
        new FanServer();
    }

    public FanServer() {
        // Place text area on the frame
        setLayout(new BorderLayout());
        add(new JScrollPane(jta), BorderLayout.CENTER);

        setTitle("Server");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true); // It is necessary to show the frame here!

        try {
            // Create a server socket
            serverSocket = new ServerSocket(8000);
            jta.append("Server started at " + new Date() + '\n');

            // Listen for a connection request
            socket = serverSocket.accept();

            // Create data input and output streams
            ObjectOutputStream outputToClient = new ObjectOutputStream(
                    socket.getOutputStream());
            outputToClient.flush();
            ObjectInputStream inputFromClient = new ObjectInputStream(
                    socket.getInputStream());

            while (true) {
                
            	
            	FanGroup fg = (FanGroup) inputFromClient.readObject();
            	ArrayList<Fan> fans = fg.getFans();
            	double speed1 = Math.round(fans.get(0).getSpeed());
                jta.append("speed of fan 1: " + speed1 + "\n");
            	
            	//Fan f = (Fan) inputFromClient.readObject();
            	//jta.append("speed 1: "+ f.getSpeed() + "\n");
            	   
            }
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (ClassNotFoundException ex) {
            System.err.println(ex);
        }finally{
        	
        	try{
        	socket.close();
        	serverSocket.close();
        	
        	}catch(SocketException ex){
        		System.err.println(ex);
        	}catch(IOException ex){
        		System.err.println(ex);
        	
        	}
        	
        }
    }
    
}

