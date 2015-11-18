/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FanManager;

import FanManager.model.FanPane;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import FanManager.model.Fan;
import FanManager.model.FanGroup;

/**
 *
 * @author Felix & Reign
 *
 */
public class FanManager extends Application {

    private static final double WIDTH = 1050, HEIGHT = 768;
    private int FANCOUNT = 6;
    
    
    FanPane[] fanPanes = new FanPane[FANCOUNT];
    private Fan[] fanArray = new Fan[FANCOUNT];
    FanGroup fanGroup;

    private void init(Stage primaryStage) {
        HBox box = new HBox();

        // Create fan panes; current 6
        for (int i = 0; i < fanPanes.length; i++) {
            fanPanes[i] = new FanPane();
            fanArray[i] = fanPanes[i].getFan();
        }

        // Setup primary stage
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(box, WIDTH, HEIGHT));
        primaryStage.setTitle("Fan Manager");

        // Create a scroll pane for multiple fan blades
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // Add scroll panel to HBox container
        box.getChildren().add(scrollPane);
        HBox.setHgrow(scrollPane, Priority.ALWAYS);

        // Create tile pane for fan panes
        TilePane tilePane = new TilePane();
        tilePane.setPrefColumns(3);
        tilePane.setHgap(-20);

        // Add fan panes to tile pane
        for (FanPane fanPane : fanPanes) {
            tilePane.getChildren().add(fanPane);
        }

        // Add tile pane to scroll pane
        scrollPane.setContent(tilePane);
        scrollPane.getStylesheets().add(getClass().getResource("view/knobObject.css").toExternalForm());

        // Create fan group from fan Array
        fanGroup = new FanGroup(fanArray, fanArray[0].getTemperature(), 40, 50);
    }

    public double getSampleWidth() {
        return 495;
    }

    public double getSampleHeight() {
        return 480;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
        Thread networking = new Thread(new Networking());
        networking.setDaemon(true);
        networking.start(); /* network hanging in background after app is closed */

    }

    public static void main(String[] args) {
        launch(args);
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
                System.err.println(ex);
            } finally {
                try {
                    socket.close();
                    serverSocket.close();
                } catch (SocketException ex) {
                    System.err.println(ex);
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }
        }

        private void sendData() {
            try {

                while (true) {

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
                System.err.println(ex);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                System.err.println(ex);
            } finally {
                try {
                    socket.close();
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
    }
}
