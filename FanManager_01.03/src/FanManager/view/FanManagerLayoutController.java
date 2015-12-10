/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FanManager.view;

import FanManager.model.Fan;
import FanManager.model.FanGroup;
import FanManager.model.FanPane;
import FanManager.Networking;
import FanManager.TCPClient;
import FanManager.model.FanPane;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import FanManager.view.RootLayoutController;
import javafx.scene.control.Slider;

/**
 * FXML Controller class
 *
 * @author reign
 */
public class FanManagerLayoutController implements Initializable {
   
    private String tempString;
    private String humidString;
    private String pressureString;
    
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
    

    private int FANCOUNT = 6;

    FanPane[] fanPanes = new FanPane[FANCOUNT];
    private ObservableList<Fan> fanList = FXCollections.observableArrayList();
//    private Fan[] fanArray = new Fan[FANCOUNT];
    FanGroup fanGroup;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Create fan panes; current 6
        for (int i = 0; i < fanPanes.length; i++) {
            fanPanes[i] = new FanPane(this);
            fanList.add(fanPanes[i].getFan());
        }

        // Create tile pane for fan panes
        TilePane tilePane = new TilePane();
        tilePane.setPrefColumns(3);
        tilePane.setHgap(-20);

        // Add fan panes to tile pane
        for (FanPane fanPane : fanPanes) {
            tilePane.getChildren().add(fanPane);
        }

        fanGroup = new FanGroup(fanList.toArray(new Fan[0]),
                (fanList.get(0)).getTemperature(), 40, 50);

        Thread networking = new Thread(new Networking(this));
        networking.setDaemon(true);
        networking.start();
        
        Thread mySerial = new Thread(new TCPClient(this));
        mySerial.setDaemon(true);
        mySerial.start();


        // Add tile pane to scroll pane
        scrollPane.setContent(tilePane);
        scrollPane.getStylesheets().add("FanManager/view/knobObject.css");

       
    }

    public ObservableList<Fan> getFanList() {
        return fanList;
    }

    public FanGroup getFanGroup() {
        fanGroup.setFans(new ArrayList<Fan>(fanList));
        return fanGroup;
    }

    public FanPane[] getFanPanes() {
        return fanPanes;
    }

    public synchronized void updateFanList(double speed, double freq, boolean power, int id) {
        fanList.set(id, new Fan(speed, freq, power, fanList.get(id))); //should send "true" because when we move the gauge, we want it to be alive
        fanPanes[id].setFan(fanList.get(id));
//System.out.println("Ipower = " + power);
//        if (power==false) fanPanes[id].turnOnButton(); //need to call this to change design to on if it's currently off
//            System.out.println("Inside updateFanList");
//System.out.println("Opower = " + power);
    }
    @FXML
    private void handleSystemOff() throws IOException {
        int i = 0;
        for( i = 0; i < FANCOUNT; i ++)
        {
            fanList.get(i).turnOff();
            fanPanes[i].getSpeedKnob().adjustValue(0);
            fanPanes[i].getFreqKnob().adjustValue(0);
            fanPanes[i].turnOffButton(); 
            //        System.out.println("Turning off all Fans");
//            updateFanList(fan.getSpeed(), fan.getFreq, fan.getPower, fan.isOn);

        }
    }
    
    public synchronized void updateTempList(double temp, double humidity, double pressure) {
        tempString = String.valueOf(temp);
        this.temperatureLabel.setText(tempString);
        
        humidString = String.valueOf(humidity);
        this.humidityLabel.setText(humidString);

        pressureString = String.valueOf(pressure);
        this.barometerLabel.setText(pressureString);
    }

}

