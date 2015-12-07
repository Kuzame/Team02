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

<<<<<<< HEAD
    public void updateFanList(double speed,double freq ,int id) {
=======
    public synchronized void updateFanList(double speed, int id) {
>>>>>>> origin/master
        //fanList.get(id).setSpeed(speed);
        fanList.set(id, new Fan(speed, freq, fanList.get(id)));
//        fanList.set(id, new Fan(freq, fanList.get(id)));
        fanPanes[id].setFan(fanList.get(id));
    }

   }

