package FanManager.view;

import FanManager.FanManager;
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
//    private FanManager mainApp;

    // Reference to the main application.

    /**
     * Initializes the controller class.
     */
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        // TODO
//    }    
    
//     /**
//     * Is called by the main application to give a reference back to itself.
//     * 
//     * @param mainApp
//     */
//    public void setMainApp(FanManager mainApp) {
//        System.out.println("pre-mainApp");
//        this.mainApp = mainApp;
//        System.out.println("post-mainApp");
//
//    }
    
    
    // Opens an about dialog.
    @FXML
    private void handleAbout() {
    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
    	alert.setTitle("FanManager");
    	alert.setHeaderText("About");
    	alert.setContentText("Author: Reign\nWebsite: www.reign-tech.com");

    	alert.showAndWait();
    }
    // Closes the application.
    @FXML
    private void handleExit() {
        System.exit(0);
    }

}