package FanManager.view;

import FanManager.FanManager;
import FanManager.MyRxTx;
import FanManager.TCPClient;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author Reign
 */
public class RootLayoutController {
    
    private static boolean networkOn = false;
    private static boolean bluetoothOn = false;

    
    
    // Opens an about dialog.
    @FXML
    private void handleAbout() {
    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
    	alert.setTitle("FanManager");
    	alert.setHeaderText("About");
    	alert.setContentText("Author: CS56 Team 02 Fall 2015\nReign DeRenzo   Website: www.reign-tech.com\nFelix\nChad\nKristen\nAdrian\nOmid\nDishon\nJorge\nPLEASE FILL THIS IN!!!");

    	alert.showAndWait();
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
//    console.add( new JScrollPane( textComponent ) );
//    MessageConsole mc = new MessageConsole(textComponent);
//    mc.redirectOut();
//    mc.redirectErr(Color.RED, null);
//    mc.setMessageLines(100);        
        System.out.println("handleConsole");
    }


}