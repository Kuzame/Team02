/*
 * Notes: 
 * 
 */
package FanManager.model;

import FanManager.GaugeObject;
import FanManager.view.Section;
import FanManager.FreqInputField;
import FanManager.SpeedInputField;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Reign & Felix
 */
public class FanPane extends FlowPane {

    private static final double WIDTH = 350, HEIGHT = 768;
    private static int nextId = 0;
    private int id;
    
    // Gauge
    private static final Random RND = new Random();
    private Section[]           sections;
    private long                lastTimerCall;
    private AnimationTimer      timer;
    private GaugeObject         gauge;

    //values to hold for Input Text Fields
    private double              freqValue;
    private double              speedValue;

    // Input Text Fields
    private SpeedInputField     speedField;
    private FreqInputField      freqField;
 
    // Knobs
    private Slider              speedKnob;
    private Slider              freqKnob;

    // Label
    final private Label         speedInputLabel = new Label();

    // Fan Object
    private Fan                 fan;

    // Temperature 
    TextField                   temperatureTF;

    public FanPane() {
        // Create an individual ID for Fan Pane
        id = ++nextId;

        // Create a maximum width and height for predictable columns
        setMaxWidth(WIDTH);
        setMaxHeight(HEIGHT);

        // Create fan object
        fan = new Fan();

        // Create gauge
        gauge = gauge();
        gauge.setTranslateX((int) (WIDTH / 24));
        gauge.setTranslateY((int) (HEIGHT / 32));
        gauge.setStyle("-fx-background-color: transparent");

    // Temperature
        // Create and style temperature label
        Label temperatureLabel = new Label("Temperature");
        temperatureLabel.setTranslateX((int) (WIDTH / 2) - 150);
        temperatureLabel.setTranslateY((int) (HEIGHT / 4) + 200);
        temperatureLabel.setStyle("-fx-background-color: green");

        // Create temperature text field
        temperatureTF = new TextField();
        temperatureTF.setTranslateX((int) (WIDTH / 2) - 70);
        temperatureTF.setTranslateY((int) (HEIGHT / 4) + 200);
        temperatureTF.setText("" + fan.getTemperature());

        
    // Speed
        // Create and style speed label
        Label speedLabel = new Label("Speed");
        speedLabel.setTranslateX((int) (WIDTH / 2) - 130);
        speedLabel.setTranslateY((int) (HEIGHT / 4) + 175);
        speedLabel.setStyle("-fx-text-fill: white;");

        // Create speed Knob
	speedKnob = new Slider(0,100,0);
	speedKnob.setBlockIncrement(0.1);
	speedKnob.setId("knob");
	speedKnob.getStyleClass().add("knobStyle");
        speedKnob.setTranslateX((int) (WIDTH / 2) - 90);
        speedKnob.setTranslateY((int) (HEIGHT / 4) + 150);

        // Create input textfields
        speedField = SpeedInputField();

        // Locate speedField
        speedField.setTranslateX((int) (WIDTH / 16));
        speedField.setTranslateY((int) (HEIGHT / 32) + 370);
        speedField.valueProperty().bindBidirectional(gauge.valueProperty());
        speedField.valueProperty().bindBidirectional(speedKnob.valueProperty());
        speedField.setPrefWidth(75);
    
    // Speed Input Label
        // Create input label
        speedInputLabel.setStyle("-fx-color: white");    
        speedInputLabel.setStyle("-fx-font: 12 arial;"); 
        speedInputLabel.setStyle("-fx-background-color: grey");
        speedInputLabel.setTranslateX((int) (WIDTH / 16));
        speedInputLabel.setTranslateY((int) (HEIGHT / 32 + 280));
        speedInputLabel.setPrefWidth(50);        
        
    // Frequency        
        // Create and style frequency label
        Label freqLabel = new Label("Frequency");
        freqLabel.setTranslateX((int) (WIDTH / 2) - 150);
        freqLabel.setTranslateY((int) (HEIGHT / 4) + 370);
        freqLabel.setStyle("-fx-text-fill: white;");

        // Create frequency Knob
	freqKnob = new Slider(0,40000,40);
	freqKnob.setBlockIncrement(1);
	freqKnob.setId("knob");
	freqKnob.getStyleClass().add("knobStyle");
        freqKnob.setTranslateX((int) (WIDTH / 2) - 90);
        freqKnob.setTranslateY((int) (HEIGHT / 4) + 350);

        // Create input textfields        
        freqField = FreqInputField();
        
        // Locate freqField
        freqField.setPrefWidth(75);
        freqField.setTranslateX((int) (WIDTH / 16));
        freqField.setTranslateY((int) (HEIGHT / 32) + 560);
        freqField.valueProperty().bindBidirectional(freqKnob.valueProperty());

        // Get values from speedKnob and the gauge to display in the input label
        speedInputLabel.setText(Math.round(speedKnob.getValue()) + "");
        speedInputLabel.setText(Math.round(gauge.getValue()) + "");
        gauge.valueProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
            if (newValue == null) {
              speedInputLabel.setText("");
              return;
            }
        speedInputLabel.setText((newValue.doubleValue()) + "");
            }   
        });
        speedKnob.valueProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
            if (newValue == null) {
                speedInputLabel.setText("");
                return;
        }
        speedInputLabel.setText(Math.round(newValue.intValue()) + "");
            }
        });   

        // Create background fill
        Rectangle background = new Rectangle(WIDTH, HEIGHT, Color.BLACK);
        background.setStroke(Color.WHITE);

        
        // create main content
        Group group = new Group(
                background,
                freqLabel,
                gauge,
                speedLabel,
                speedField,
                speedInputLabel,
                speedKnob,
                freqField,
                freqKnob
            // temperatureLabel,
            // temperatureTF,

        );

        // Add main content to pane
        getChildren().add(group);
        


        // Event handler: 
        //   Inputs:    Temperature Data
        //   Performs:  Turn On/Off/Changes Speed of Fan animation
        //   Outputs:   None
        
        temperatureTF.setOnAction((ActionEvent e) -> {
        System.out.println(id + ": " + temperatureTF.getText());

        // Update Temperature
        fan.setTemperature(Double.parseDouble(temperatureTF.getText()));
        });

        speedKnob.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val) {

                System.out.println(id + ": " + speedKnob.getValue());

                // Update Fan Speed
                fan.setSpeed(speedKnob.getValue());
            }
        });
    }


    // Fan
    public Fan getFan() {
        return fan;
    }

    public void setFan(Fan fan) {
        this.fan = fan;
        temperatureTF.setText("" + fan.getTemperature());
        speedKnob.setValue(fan.getSpeed());
    }

    
    // InputTextFields
    private SpeedInputField SpeedInputField() {
        speedField = new SpeedInputField(0, 100, 0);
        return speedField;
        }
    
    private FreqInputField FreqInputField() {
        freqField = new FreqInputField(40, 40000, 40);
        return freqField;
        }

    // Create Gauge

    public final GaugeObject gauge() {
        sections = new Section[] {
            new Section(0, 9.9, Color.rgb(64, 182, 75)),
            new Section(9.9, 10.1, Color.rgb(255, 255, 255)),
            new Section(10.1, 19.9, Color.rgb(64, 182, 75)),
            new Section(19.9, 20.1, Color.rgb(255, 255, 255)),
            new Section(20.1, 29.9, Color.rgb(64, 182, 75)),
            new Section(29.9, 30.1, Color.rgb(255, 255, 255)),
            new Section(30.1, 39.9, Color.rgb(64, 182, 75)),
            new Section(39.9, 40.1, Color.rgb(255, 255, 255)),
            new Section(40.1, 49.9, Color.rgb(64, 182, 75)),
            new Section(49.9, 50.1, Color.rgb(255, 255, 255)),
            new Section(50.1, 59.9, Color.rgb(64, 182, 75)),
            new Section(59.9, 60.1, Color.rgb(255, 255, 255)),
            new Section(60.1, 69.9, Color.rgb(64, 182, 75)),
            new Section(69.9, 70.1, Color.rgb(255, 255, 255)),
            new Section(70.1, 79.9, Color.rgb(64, 182, 75)),
            new Section(79.9, 80.1, Color.rgb(255, 255, 255)),
            new Section(80.1, 89.9, Color.rgb(209, 184, 74)),
            new Section(89.9, 90.1, Color.rgb(255, 255, 255)),
            new Section(90.1, 99.9, Color.rgb(209, 78, 74)),
            new Section(99.9, 100, Color.rgb(255, 255, 255))
//            new Section(0, 100, Color.rgb(64, 182, 75))
            };
        
        gauge = new GaugeObject();
        gauge.setMinValue(0);
        gauge.setMaxValue(100);
        gauge.setThreshold(80);
        gauge.setSections(sections);
        lastTimerCall = System.nanoTime();
        timer = new AnimationTimer() {
            @Override public void handle(long now) {
                if (now > lastTimerCall + 3_000_000_000l) {
                    gauge.setValue(RND.nextDouble() * 30.0 + 10);
                    lastTimerCall = now;
                }
            }
        };
        return gauge;
        };


// helper eventhandler that allows a text label to be edited within the range of a slider.
  private EventHandler<MouseEvent> newLabelEditHandler(final Label text, final GaugeObject gauge) {
    return new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent mouseEvent) {
        final TextField editField = new TextField(text.getText());
        text.setGraphic(editField);
        text.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        editField.setTranslateX(-text.getGraphicTextGap() - 1);
        editField.setTranslateY(-3); // adjustment hack to get alignment right.
        editField.requestFocus();
        editField.selectAll(); // hmm there is a weird bug in javafx here, the text is not selected,
                               // but if I focus on another window by clicking only on title bars,
                               // then back to the javafx app, the text is magically selected.
 
        editField.setOnKeyReleased(new EventHandler<KeyEvent>() {
          @Override public void handle(KeyEvent t) {
            if (t.getCode() == KeyCode.ENTER) {
              text.setContentDisplay(ContentDisplay.TEXT_ONLY);
 
              // field is empty, cancel the edit.
              if (editField.getText() == null || editField.getText().equals("")) {
                return;
              }
 
              try {
                double editedValue = Double.parseDouble(editField.getText());
                if (gauge.getMinValue() <= editedValue && editedValue <= gauge.getMaxValue()) {
                  // edited value was within in the valid slider range, perform the edit.
                  gauge.setValue(Integer.parseInt(editField.getText()));
                  text.setText(editField.getText());
                }
              } catch (NumberFormatException e) { // a valid numeric value was not entered,
                text.setContentDisplay(ContentDisplay.TEXT_ONLY);
              }
            } else if (t.getCode() == KeyCode.ESCAPE) {
              text.setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
          }
        });
      }
    };
  }


// helper eventhandler that allows a text label to be edited within the range of a slider.
  private EventHandler<MouseEvent> newLabelEditHandler(final Label text, final Slider slider) {
    return new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent mouseEvent) {
        final TextField editField = new TextField(text.getText());
        text.setGraphic(editField);
        text.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        editField.setTranslateX(-text.getGraphicTextGap() - 1);
        editField.setTranslateY(-3); // adjustment hack to get alignment right.
        editField.requestFocus();
        editField.selectAll();
 
        editField.setOnKeyReleased(new EventHandler<KeyEvent>() {
          @Override public void handle(KeyEvent t) {
            if (t.getCode() == KeyCode.ENTER) {
              text.setContentDisplay(ContentDisplay.TEXT_ONLY);
 
              // field is empty, cancel the edit.
              if (editField.getText() == null || editField.getText().equals("")) {
                return;
              }
 
              try {
                double editedValue = Double.parseDouble(editField.getText());
                if (slider.getMin() <= editedValue && editedValue <= slider.getMax()) {
                  // edited value was within in the valid slider range, perform the edit.
                  slider.setValue(Integer.parseInt(editField.getText()));
                  text.setText(editField.getText());
                }
              } catch (NumberFormatException e) { // a valid numeric value was not entered,
                text.setContentDisplay(ContentDisplay.TEXT_ONLY);
              }
            } else if (t.getCode() == KeyCode.ESCAPE) {
              text.setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
          }
        });
      }
    };
  }



// End FanPane Class
}
