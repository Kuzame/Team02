/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FanManager;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/**
 *
 * @author Felix
 *      
 */
public class FanManager extends Application {
    
    private static final double WIDTH = 1024, HEIGHT = 768;
    FanPane[] fanPanes = new FanPane[3];

    private void init(Stage primaryStage) {
        HBox box = new HBox();

        // Create fan panes; current 6
        for (int i = 0; i < fanPanes.length; i++) {
            fanPanes[i] = new FanPane();
        }

        // Setup primary stage
        primaryStage.setWidth(1013);
        primaryStage.setHeight(768);
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
        scrollPane.getStylesheets().add(getClass().getResource("view/sliderObject.css").toExternalForm());

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
    }

    public static void main(String[] args) {
        launch(args);
    }
}
