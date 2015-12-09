/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FanManager;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Reign
 */
public class FreqInputField extends Label {
   // helper text field subclass which restricts text input to a given range of natural int numbers
  // and exposes the current numeric int value of the edit box as a value property.
    private DoubleProperty value;
    private double minValue;
    private double maxValue;
 
    // expose an integer value property for the text field.
    double  getValue()                 { return value.getValue(); }
    public void setValue(int newValue)     { value.setValue(newValue); }
    public DoubleProperty valueProperty() { return value; }
    
    public FreqInputField(int minValue, int maxValue, int initialValue) {
      if (minValue > maxValue) 
        throw new IllegalArgumentException(
          "IntField min value " + minValue + " greater than max value " + maxValue
        );
      if (maxValue < minValue) 
        throw new IllegalArgumentException(
          "IntField max value " + minValue + " less than min value " + maxValue
        );
      if (!((minValue <= initialValue) && (initialValue <= maxValue))) 
        throw new IllegalArgumentException(
          "IntField initialValue " + initialValue + " not between " + minValue + " and " + maxValue
        );
 
      // initialize the field values.
      this.minValue = minValue;
      this.maxValue = maxValue;
      value = new SimpleDoubleProperty(initialValue);
      
      setText(initialValue + "");
 
      FreqInputField inputField = this;
 
      // make sure the value property is clamped to the required range
      // and update the field's text to be in sync with the value.
      value.addListener(new ChangeListener<Number>() {
        @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
          if (newValue == null) {
            inputField.setText("");
          } else {
            if (newValue.intValue() < inputField.minValue) {
              value.setValue(inputField.minValue);
              return;
            }
 
            if (newValue.intValue() > inputField.maxValue) {
              value.setValue(inputField.maxValue);
              return;
            }
 
            if (newValue.intValue() == 0 && (textProperty().get() == null || "".equals(textProperty().get()))) {
              // no action required, text property is already blank, we don't need to set it to 0.
            } else {
                Platform.runLater(new Runnable() {
                @Override
                public void run() {

                   inputField.setText(newValue.toString());
                }
            });
              
            }
          }
        }
      });
 
      // restrict key input to numerals.
      this.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
        @Override public void handle(KeyEvent keyEvent) {
          if (!"0123456789".contains(keyEvent.getCharacter())) {
            keyEvent.consume();
          }
        }
      });
      
      // ensure any entered values lie inside the required range.
      this.textProperty().addListener(new ChangeListener<String>() {
        @Override public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
          if (newValue == null || "".equals(newValue)) {
            value.setValue(0);
            return;
          }
 
          final double intValue = Double.parseDouble(newValue);
 
          if (inputField.minValue > intValue || intValue > inputField.maxValue) {
            textProperty().setValue(oldValue);
          }
          
          value.set(Double.parseDouble(textProperty().get()));
        }
      });
    }
}   

