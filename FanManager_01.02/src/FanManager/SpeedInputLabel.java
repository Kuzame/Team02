/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FanManager;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;

/**
 *
 * @author Reign
 */
//public class SpeedInputLabel {
//        final private Label text = new Label();
//        SpeedInputLabel(int speedSlider, int gauge){
//    text.setStyle("-fx-color: white");    
//    text.setStyle("-fx-font: 12 arial;");    
//    text.setText(Math.round(speedSlider.getValue()) + "");
//    text.setText(Math.round(gauge.getValue()) + "");
//    gauge.valueProperty().addListener(new ChangeListener<Number>() {
//      @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
//        if (newValue == null) {
//          text.setText("");
//          return;
//        }
//        text.setText((newValue.intValue()) + "");
//      }
//    });
//     speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
//      @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
//        if (newValue == null) {
//          text.setText("");
//          return;
//        }
//        text.setText(Math.round(newValue.intValue()) + "");
//      }
//    });   
//
//    text.setPrefWidth(50);        // allow the label to be clicked on to display an editable text field and have a slider movement cancel any current edit.
//    text.setOnMouseClicked(newLabelEditHandler(text, speedSlider));
//    speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
//      @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
//        text.setContentDisplay(ContentDisplay.TEXT_ONLY);
//      }
//    });
//        }
//}
