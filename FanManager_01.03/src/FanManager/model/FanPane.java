/*
 * Notes: 
 * 
 */
package FanManager.model;

import FanManager.GaugeObject;
import FanManager.view.Section;
import FanManager.FreqInputField;
import FanManager.SpeedInputField;
import FanManager.view.FanManagerLayoutController;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
    private Section[] sections;
    private long lastTimerCall;
    private AnimationTimer timer;
    private GaugeObject gauge;

    // power
    private boolean power;
    
    //values to hold for Input Text Fields
    private double freqValue;
    private double speedValue;

    // Input Text Fields
    private SpeedInputField speedField;
    private FreqInputField freqField;

    // Knobs
    private Slider speedKnob;
    private Slider freqKnob;
    
    //Button
    Button powerButton = new Button();

    // Label
    final private Label tempLabel = new Label();

    // Fan Object
    private Fan fan;
    private FanManagerLayoutController mainApp;

    // Temperature 
    TextField temperatureTF;

    public FanPane(FanManagerLayoutController mainApp) {
        this.mainApp = mainApp;

        // Create an individual ID for Fan Pane
        id = nextId++;

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
        speedKnob = new Slider(0, 100, 0);
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
        speedKnob.valueProperty().bindBidirectional(gauge.valueProperty());
        speedField.valueProperty().bindBidirectional(speedKnob.valueProperty());
        speedField.setPrefWidth(75);

        // Speed Input Label
        // Create input label
        tempLabel.setStyle("-fx-color: white");
        tempLabel.setStyle("-fx-font: 12 arial;");
        tempLabel.setStyle("-fx-background-color: grey");
        tempLabel.setTranslateX((int) (WIDTH / 16));
        tempLabel.setTranslateY((int) (HEIGHT / 32 + 280));
        tempLabel.setPrefWidth(50);

        // Frequency        
        // Create and style frequency label
        Label freqLabel = new Label("Frequency");
        freqLabel.setTranslateX((int) (WIDTH / 2) - 150);
        freqLabel.setTranslateY((int) (HEIGHT / 4) + 370);
        freqLabel.setStyle("-fx-text-fill: white;");

        // Create frequency Knob
        freqKnob = new Slider(0, 100, 0);
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
//        freqField.valueProperty().bindBidirectional(freqKnob.valueProperty());

//        // Get values from speedKnob and the gauge to display in the input label
        speedField.setText(Math.round(speedKnob.getValue()) + "");
        speedField.setText(Math.round(gauge.getValue()) + "");

        powerButton.setStyle(
         "-fx-background-color: \n" +
"        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\n" +
"        linear-gradient(#020b02, #3a3a3a),\n" +
"        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\n" +
"        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\n" +
"        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);"+
        "-fx-background-insets: 2px; " +
        "-fx-border-width: 1px;"+
        "-fx-border-color: red;" +
        "-fx-padding: 0px;"
        
        );
        powerButton.setId("fanButton");
        
         
        double r=30;
        powerButton.setShape(new Circle(r));
        powerButton.setMinSize(2*r, 2*r);
        powerButton.setMaxSize(2*r, 2*r);
        
//        powerButton.getStyleClass().add("fanPower");

        powerButton.setTranslateX((int) (WIDTH / 2) -41);
        powerButton.setTranslateY( 143);
        powerButton.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override public void handle(ActionEvent e) 
            {
                Fan fan = getFan();
                if(fan.isOn())
                {
//                    turnOffButton();//PS: We have to decide where to call this function only once, not multiple times since we also called it in fanOff(below)
                    fanOff(fan);
                    
                }
                else
                {  
                    turnOnButton();
                }
            }
        });
        

       
        speedKnob.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (!speedKnob.isValueChanging()) {
                            if (newValue == null) {
                                double roundOff = (double) Math.round(speedKnob.getValue() * 100) / 100;
                                String total2 = String.valueOf(roundOff);
                                speedField.setText(total2);
                                return;
                            }
                                double roundOff = (double) Math.round(speedKnob.getValue() * 100) / 100;
                                String total2 = String.valueOf(roundOff);
                                speedField.setText(total2);

                            //fan.setSpeed(speedKnob.getValue());
                            mainApp.updateFanList(speedKnob.getValue(), freqKnob.getValue(), power, id); 
                            System.out.println("Speed updated");
                        }
                    }
                });
            }
        });
        
        freqKnob.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (!freqKnob.isValueChanging()) {
                            if (newValue == null) {
                                double roundOff2 = (double) Math.round(freqKnob.getValue() * 100) / 100;
                                String total3 = String.valueOf(roundOff2);
                            freqField.setText(total3);
                                return;
                            }
//                            freqField.setText((freqKnob.getValue()) + "");
                                double roundOff2 = (double) Math.round(freqKnob.getValue() * 100) / 100;
                                String total3 = String.valueOf(roundOff2);
                            freqField.setText(total3);
                            //fan.setSpeed(speedKnob.getValue());
                            mainApp.updateFanList(speedKnob.getValue(), freqKnob.getValue(), power, id);
                            System.out.println("Freq updated");
                        }
                    }
                });
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
                tempLabel,
                speedKnob,
                freqField,
                freqKnob,
                powerButton
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

    }
    
    //simply call these functions if want to turn off/on button (image only, not turning it off functionally)
    public void turnOnButton() {
        powerButton.setStyle(
                       "-fx-background-color: \n" +
                       "linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%);" +
                       "-fx-background-insets: 2px; " +
                       "-fx-border-width: 1px;" +
                       "-fx-border-color: limegreen;" +
                       //"-fx-border-width: 10px"+
                       "-fx-padding: 0px;");
        
        power = true; //It's more reliable to have this single public function that do 1 exact thing for everyone
        fan.turnOn(true,50,40);
        System.out.println("Turning on the power");
    }
    
    public void turnOffButton() {
        powerButton.setStyle(
                        "-fx-background-color: \n" +
               "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\n" +
               "        linear-gradient(#020b02, #3a3a3a),\n" +
               "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\n" +
               "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\n" +
               "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);"+
                       "-fx-background-insets: 2px; " +       
                       "-fx-border-width: 1px;"+
                       "-fx-border-color: red;" +
                       "-fx-padding: 0px;"
                    );
        
        power = false; //Same reason as in turnOnButton
        fan.setPower(false);
        System.out.println("Turning off the power");
    }
    
    public void fanOff(Fan fan){
            fan.setPower(false);
            fan.turnOff();
            turnOffButton(); 
            power = false;
            speedKnob.adjustValue(0);
            freqKnob.adjustValue(0);
//            System.out.println("Turning off the All Fans"); //Even turning off 1 button will need to call the function, so this shouldn't be here

        }

    // Fan
    public Fan getFan() {
        return fan;
    }

    public void setFan(Fan fan) {
        this.fan = fan;

    }

    // InputTextFields
    private SpeedInputField SpeedInputField() {
        speedField = new SpeedInputField(0, 100, 0);
        return speedField;
    }

    private FreqInputField FreqInputField() {
        freqField = new FreqInputField(0, 100, 0);
        return freqField;
    }

    // Create Gauge
    public final GaugeObject gauge() {
        sections = new Section[]{
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
            @Override
            public void handle(long now) {
                if (now > lastTimerCall + 3_000_000_000l) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            gauge.setValue(RND.nextDouble() * 30.0 + 10);
                        }
                    });
                    lastTimerCall = now;

                }
            }
        };
        return gauge;
    }

    public synchronized void updateGauge() {
        try {
            Platform.runLater(() -> gauge.setValue(fan.getSpeed()));
            Thread.sleep(5);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    public Slider getSpeedKnob()
    {
        return speedKnob;
    }
    public Slider getFreqKnob()
    {
        return freqKnob;
    }

}
