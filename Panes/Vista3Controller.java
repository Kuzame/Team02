import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Vista3Controller extends Application {

    @Override
    public void start(Stage primaryStage) {
    	primaryStage.initStyle(StageStyle.TRANSPARENT);
    }

    @FXML
    void firstPane(ActionEvent event) {
        VistaNavigator.loadVista(VistaNavigator.VISTA_1);
    }

    
}