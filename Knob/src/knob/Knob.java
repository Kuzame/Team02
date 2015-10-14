package knob;



//import Knob.Knob;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;




public class Knob  extends Application {
    private MediaPlayer mediaPlayer;
    private Slider balanceKnob, volumeKnob;


    @Override public void start(final Stage primaryStage) {

    	balanceKnob = new Slider(-1,1,0);
        balanceKnob.setBlockIncrement(0.1);
        balanceKnob.setId("balance");
        balanceKnob.getStyleClass().add("knobStyle");
        balanceKnob.valueProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
                if(mediaPlayer != null) mediaPlayer.setBalance(newValue.doubleValue());
            }
        });
        volumeKnob = new Slider(0,1,1);
        volumeKnob.setBlockIncrement(0.1);
        volumeKnob.setId("volume");
        volumeKnob.getStyleClass().add("knobStyle");
        volumeKnob.valueProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
                if(mediaPlayer != null) mediaPlayer.setVolume(newValue.doubleValue());
            }
        });
        
        Group root = new Group();
        root.setAutoSizeChildren(false);
        root.getChildren().addAll(balanceKnob, volumeKnob);
        balanceKnob.resizeRelocate(40,50,87,87);
        volumeKnob.resizeRelocate(0,200,175,175);

        Scene scene = new Scene(root, 200,400);
        scene.setFill(Color.WHITE);
        scene.getStylesheets().add(Knob.class.getResource("Knob.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    	}

    
    
    
    
    
    
    /** @param args the command line arguments */
    public static void main(String[] args) {
        launch(args);
    }
     
}
