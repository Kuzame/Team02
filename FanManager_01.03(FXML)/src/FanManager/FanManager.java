/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FanManager;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Felix & Reign
 *
 */
public class FanManager extends Application {

//    private static final double WIDTH = 1050, HEIGHT = 768;
    
    private Stage primaryStage;
    private BorderPane rootLayout;

    
    
    private void init(Stage primaryStage) {
        // Setup primary stage
        this.primaryStage = primaryStage;
        this.primaryStage.setResizable(true);
        this.primaryStage.setTitle("Fan Manager");
        // Load root layout from fxml file.
        initRootLayout();
        showFanManagerLayout();
//        // Create fan group from fan Array
//        fanGroup = new FanGroup(fanArray, fanArray[0].getTemperature(), 40, 50);

    }


    @Override
    public void start(Stage primaryStage){
        init(primaryStage);
        primaryStage.show();
    }


    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(FanManager.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            
            primaryStage.show();
        } catch (IOException e) {
                       System.out.println("IOException in initRootLayout");

            e.printStackTrace();
        }
    }
    
    
    public void showFanManagerLayout() {
    try {
        // Load FanManagerLayout.

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FanManager.class.getResource("view/FanManagerLayout.fxml"));
        AnchorPane fanManagerLayout = (AnchorPane) loader.load();

        // Set FanManager Layout into the center of root layout.
        rootLayout.setCenter(fanManagerLayout);

    } catch (IOException e) {
        e.printStackTrace();
    }

    }



    public static void main(String[] args) {
        launch(args);
    }
    

}
