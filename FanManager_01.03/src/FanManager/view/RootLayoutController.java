package FanManager.view;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Reign
 */
public class RootLayoutController {
    
    private static boolean networkOn = false;
    private static boolean bluetoothOn = false;
    private Stage consoleStage;
    private PrintStream printStream;

    
    
    // Opens an about dialog.
    @FXML
    private void handleAbout() {
        /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("FanManager");
        alert.setHeaderText("About");
        alert.setContentText("Author: CS56 Team 02 Fall 2015\nReign DeRenzo   Website: www.reign-tech.com\nFelix\nChad\nKristen\nAdrian\nOmid\nDishon\nJorge\nPLEASE FILL THIS IN!!!");
        
        alert.showAndWait();*/
    }
    // Closes the application.
    @FXML
    private void handleExit() throws IOException {
//        networkOn = false;
//        socket.close();
//        serverSocket.close();
//        networking.interrupt();
        System.exit(0);
    }
    
    @FXML
    private void handleLANOn() {
//        if (!networking.isAlive())
//        {
//        networking.start(); /* network hanging in background after app is closed */
        networkOn = true;
        System.out.println("networking.start");
//        }else{ 
//        networking.resume();
//        networkOn = true;
//        System.out.println("networking.resume");

//        }
    }

    @FXML
    private void handleLANOff() throws IOException {
//           networking.suspend();
           networkOn = false;
//           stopSockets();
           System.out.println("handleLANOff");
    }

    @FXML
    private void handleBluetooth() throws IOException {
//        Thread mySerial = new Thread(new TCPClient(this));
//                mySerial.setDaemon(true);
//                mySerial.start();

           if (bluetoothOn == false)
           {
                bluetoothOn = true;
           }
           else
           {
                bluetoothOn = false;
           }
           
           System.out.println("handleBluetooth");
    }
    
     
    @FXML
    private void handleConsole() throws IOException {

//        System.setErr(new PrintStream(Console.getInstance(rb)));

        System.out.println("handleConsole");
//                FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(FanManager.class.getResource("view/Console.fxml"));
//        loader.setResources(new ResourceBundle(){  
//      // Just a dummy resource bundle...  
//            @Override  
//            protected Object handleGetObject(String key) {  
//                return key;  
//            }  
//            @Override  
//            public Enumeration<String> getKeys() {  
//                return null;  
//            }     
//        });
//        Parent p = (Parent) loader.load(); 
//        ResourceBundle rb = p;
//

        //showConsole();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Console.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
        
    }


}