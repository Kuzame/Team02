/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FanManager.view;

import FanManager.model.Fan;
import FanManager.model.FanGroup;
import FanManager.model.FanPane;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;

/**
 * FXML Controller class
 *
 * @author reign
 */

public class FanManagerLayoutController implements Initializable {
    @FXML
    private TextField temperatureLabel;
    @FXML
    private TextField humidityLabel;
    @FXML
    private TextField barometerLabel;
    @FXML
    private TilePane fanPane;
    @FXML
    private ScrollPane scrollPane;
    
    private int FANCOUNT = 3;
    
    
    FanPane[] fanPanes = new FanPane[FANCOUNT];
    private Fan[] fanArray = new Fan[FANCOUNT];
    FanGroup fanGroup;


    
    // Initializes the controller class.
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        
        // Create fan panes; current 6
        for (int i = 0; i < fanPanes.length; i++) {
            fanPanes[i] = new FanPane();
        }

        // Create tile pane for fan panes
        TilePane tilePane = new TilePane();
        tilePane.setPrefColumns(3);
        tilePane.setHgap(-20);

        // Add fan panes to tile pane
        for (FanPane fanPane : fanPanes) {
            tilePane.getChildren().add(fanPane);
        }
        Thread networking = new Thread(new Networking());
        networking.setDaemon(true);
        networking.start(); /* network hanging in background after app is closed */

        // Add tile pane to scroll pane
        scrollPane.setContent(tilePane);
        scrollPane.getStylesheets().add("FanManager/view/knobObject.css");

    }    
      /*
     *   Private inner class to handle networking
     */
    class Networking implements Runnable {

        private ServerSocket serverSocket;
        private Socket socket;

        private ObjectOutputStream toClient;
        private ObjectInputStream fromClient;

        @Override
        public void run() {
            try {
        System.out.println("Networking");
                // Create a server socket
                serverSocket = new ServerSocket(8000);
                System.out.println("Server started at " + new Date() + '\n');

                // Listen for a connection request
                socket = serverSocket.accept();

                // Create data streams
                toClient = new ObjectOutputStream(socket.getOutputStream());
                toClient.flush();

                // Create data input
                fromClient = new ObjectInputStream(socket.getInputStream());

                // Handle data
                sendData();

            } catch (IOException ex) {
                        System.out.println("Networking try1");
                System.err.println(ex);
            } finally {
                try {
                    socket.close();
                    serverSocket.close();
                } catch (SocketException ex) {
                        System.out.println("Networking finally catch1");

                    System.err.println(ex);
                } catch (IOException ex) {
                        System.out.println("Networking finally catch2");

                    System.err.println(ex);
                }
            }
        }

        private void sendData() {
            try {
                    System.out.println("sendData try");

                while (true) {
                    System.out.println("sendData while");
                    // Read from network
                    fanGroup = (FanGroup) fromClient.readObject();

                    // Update gauge animations
                    for (int i = 0; i < fanGroup.getFans().size(); i++) {
                        fanPanes[i].setFan(fanGroup.getFans().get(i));
                        fanPanes[i].updateGauge();
                    }

                    // Write to network
                    toClient.writeObject(fanGroup);
                    toClient.reset();
                    toClient.flush();

                    Thread.sleep(1000);

                }
            } catch (ClassNotFoundException ex) {
                System.out.println("sendData catch 1");
                System.err.println(ex);
            } catch (InterruptedException ex) {
                System.out.println("sendData catch 2");
                ex.printStackTrace();
            } catch (IOException ex) {
                System.out.println("sendData catch 3");
                System.err.println(ex);
            } finally {
                try {
                    System.out.println("sendData finally try 1");
                    socket.close();
                    fromClient.close();
                    toClient.close();
                } catch (SocketException ex) {
                    System.out.println("sendData catch 3");
                    System.err.println(ex);
                    System.out.println("Fan Server Error: Finally Block");
                } catch (IOException ex) {
                    System.out.println("sendData catch 3");
                    System.err.println(ex);
                }

            }
        }
    }
}
