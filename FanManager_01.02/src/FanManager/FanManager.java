

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
    
    private Socket socket;
    private ServerSocket serverSocket;

    private static final double WIDTH = 1050, HEIGHT = 768;
    FanPane[] fanPanes = new FanPane[3];
    
    private void init(Stage primaryStage) {
        HBox box = new HBox();

        // Create fan panes; current 6
        for (int i = 0; i < fanPanes.length; i++) {
            fanPanes[i] = new FanPane();
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
//        networking.start(); /* network hanging in background after app is closed */
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    class Networking implements Runnable {
        
        @Override
        public void run() {
            try {
            // Create a server socket
            serverSocket = new ServerSocket(8000);
            System.out.println("Server started at " + new Date() + '\n');

            // Listen for a connection request
            System.out.println("Pre accept");
            socket = serverSocket.accept();
            System.out.println("Post accept");
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
                System.out.println("speed of fan 1: " + speed1 + "\n");
            	
            	FanGroup f = (FanGroup) inputFromClient.readObject();
                System.out.println("speed 1: "+ f.getFans().get(0).getSpeed() + "\n");

            	   
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
}
