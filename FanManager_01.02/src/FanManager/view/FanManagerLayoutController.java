/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FanManager.view;

<<<<<<< HEAD
import FanManager.FanManager;
=======
>>>>>>> Fixed FXML elements
import FanManager.model.FanPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
<<<<<<< HEAD
import javafx.scene.Scene;
=======
>>>>>>> Fixed FXML elements
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
<<<<<<< HEAD
import javafx.stage.Stage;
/**
 *
 * @author Reign
 */


public class FanManagerLayoutController implements Initializable  {
    private static final double WIDTH = 1050, HEIGHT = 768;
    FanPane[] fanPanes = new FanPane[6];
    

    @FXML
    private TextField temperatureLabel;
    @FXML
    private TextField barometerLabel;
    @FXML
    private TextField humidityLabel;

    // Reference to the main application.
    private FanManager mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     * @param primaryStage
     */
    public FanManagerLayoutController() {
    }
    public FanManagerLayoutController(Stage primaryStage) {
            HBox box = new HBox();
=======

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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FanPane[] fanPanes = new FanPane[6];
>>>>>>> Fixed FXML elements
        
        // Create fan panes; current 6
        for (int i = 0; i < fanPanes.length; i++) {
            fanPanes[i] = new FanPane();
        }

<<<<<<< HEAD
        primaryStage.setWidth(1013);
        primaryStage.setHeight(768);
        primaryStage.setResizable(false);
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

=======
>>>>>>> Fixed FXML elements
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
<<<<<<< HEAD
        scrollPane.getStylesheets().add(getClass().getResource("view/knobObject.css").toExternalForm());
    }

    public double getSampleWidth() {
        return 495;
    }

    public double getSampleHeight() {
        return 480;
    }
    

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
    }    

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(FanManager mainApp) {
        this.mainApp = mainApp;

    }
}
=======
        scrollPane.getStylesheets().add("FanManager/view/knobObject.css");
    }    
  
}
>>>>>>> Fixed FXML elements
