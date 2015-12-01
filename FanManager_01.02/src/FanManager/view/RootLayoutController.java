package FanManager.view;

import FanManager.FanManager;
import FanManager.MyRxTx;
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
        Thread mySerial = new Thread(new MyRxTx(this));

           if (bluetoothOn == false)
           {
                bluetoothOn = true;
                mySerial.setDaemon(true);
                mySerial.start();
           }
           else
           {
                bluetoothOn = false;
           }
           
           System.out.println("handleBluetooth");
    }
    
    
//    private static class Networking implements Runnable {
//
//        public Networking() {
//
//        }
//
//        @Override
//        public void run() {
//                        System.out.println("In Run");
////
////            SingleThreadedServer server = new SingleThreadedServer(8000);
////            new Thread(server).start();
////
////            try {
////                Thread.sleep(10 * 1000);
////            } catch (InterruptedException e) {
////            e.printStackTrace();  
////}
////            System.out.println("Stopping Server");
////            server.stop();
//        }
//        }


}