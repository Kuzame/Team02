/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FanManager;

import FanManager.model.Fan;
import FanManager.model.FanGroup;
import FanManager.model.FanPane;
import FanManager.view.FanManagerLayoutController;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 *
 * @author Felix
 *
 *   Private inner class to handle networking
 */
public class Networking implements Runnable {

    private ObjectOutputStream toClient;
    private ObjectInputStream fromClient;
    private ServerSocket server;
    private Socket client;

    private FanManagerLayoutController mainApp;
    private FanGroup fanGroup;
    private ObservableList<Fan> fanList;
    private FanPane [] fanPanes;

    public Networking(FanManagerLayoutController mainApp) {
        this.mainApp = mainApp;
        fanList = this.mainApp.getFanList();
        fanGroup = this.mainApp.getFanGroup();
        fanPanes = this.mainApp.getFanPanes();
    }

    public void run() {
        try {
            // Create a socket to connect to the server
            server = new ServerSocket(8000);
            System.out.println("Server started at " + new Date() + '\n');
            client = server.accept();
            System.out.println("client = Server accepted");

            // Create an output stream to send data to the server
            toClient = new ObjectOutputStream(client.getOutputStream());
            System.out.println("toClient: " + toClient + '\n');
            
            toClient.flush();
            System.out.println("toClient flush: " + toClient + '\n');
 
            // Create an input stream to receive data from the server
            fromClient = new ObjectInputStream(client.getInputStream());
            System.out.println("fromClient: " + fromClient + '\n');
                System.out.println(fromClient.readObject());


            fanList.addListener(new ListChangeListener() {
                @Override
                public void onChanged(ListChangeListener.Change change) {
                    System.out.println("Data changed");
                    sendData();
                }
            });
            
            // Receive server data
            Thread recieveData = new Thread(() -> recieveData());
            recieveData.setDaemon(true);
            recieveData.start();
            
            

        } catch (ConnectException ce) {
            System.err.println(ce);
        } catch (Exception ex) {
            System.out.println("Bad");
        }
    }

    private void recieveData() {
        try {
//            while (true) {
                System.out.println("Waiting for object");
                // Read from network
                FanGroup temp = (FanGroup) fromClient.readObject();
                System.out.println("Recieved object");
                
                // If new fanGroup sent over, then update each fan
                if (!fanGroup.equals(temp)) {
                    fanGroup.update(temp);
                    
                    // Update fan animations
                    for (int i = 0; i < fanGroup.getFans().size(); i++) {
                        if (!fanList.get(i).equals(fanGroup.getFans().get(i))) {
                            fanPanes[i].setFan(fanGroup.getFans().get(i));
                            fanPanes[i].updateGauge();
                        }
                    }
                }

                Thread.sleep(1000);
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                client.close();
                fromClient.close();
                toClient.close();
            } catch (SocketException ex) {
                System.err.println(ex);
                System.out.println("Fan Server Error: Finally Block");
            } catch (IOException ex) {
                System.err.println(ex);
            }

        }
    }

    private void sendData() {
        try {
                toClient.reset();
                System.out.println("sending data from manager");
                fanGroup = mainApp.getFanGroup();
                //System.out.println(fanGroup.getFans().get(0).getSpeed());

                // Write to network
                toClient.writeObject(fanGroup);
                toClient.flush();
                
                System.out.println("data sent from manager");

                
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
    }
}
