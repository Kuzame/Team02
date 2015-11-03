/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GaugeObject;

/**
 *
 * @author Reign
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import static java.awt.Color.blue;
import static java.awt.Color.green;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBuilder;
import javafx.stage.StageStyle;
//import jfxtras.labs.scene.control.gauge.Led;
//import jfxtras.labs.scene.control.gauge.LedBuilder;


/**
 *
 * @author Gerrit Grunwald <han.solo.gg at gmail.com>
 */
public class GaugeObjectDemo extends Application {
    private static final Random RND = new Random();
    private static int          noOfNodes = 0;
    private Section[]           sections;
    private GaugeObject         gauge;
    private long                lastTimerCall;
    private AnimationTimer      timer;
    final private Label text = new Label();

    public static final String LABEL_TOOLTIP  =
    "Click on me to edit the gauges value.\n" +
    "The gauge will be updated when you hit the Enter key to commit the edit.\n" +
    "If you do not enter a value in the gauges range,\n" +
    "then the entered value will be ignored.\n" +
    "You can hit the Escape key or move the slider to cancel an edit.";
    public static final String EDIT_FIELD_TOOLTIP =
    "Click on me to edit the gauge's value,\n" +
    "but you will only be able to enter numeric values within the gauge's range.\n" +
    "The gauge will be updated as you type\n" +
    "and there is no need to commit the edit with an enter key.";

    @Override public void init() {
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
//                    gauge.setValue(RND.nextDouble() * 30.0 + 10);
                    lastTimerCall = now;
                }
            }
        };
    }

    @Override public void start(Stage stage) {
            // bind a plain text label to the slider value.
//  StackPane pane=new StackPane();
//  Rectangle background=new Rectangle(600,600);
//  background.setFill(Color.rgb(30,30,30));
//  pane.getChildren().add(background);
  final GridPane gridpane=new GridPane();
  gridpane.setPadding(new Insets(5));
  gridpane.setAlignment(Pos.TOP_CENTER);
//  for (int row=0; row < 2; row++) {
//    ArrayList<Led> column=new ArrayList<Led>(3);
//    java.util.Vector rows = new java.util.Vector();
//    for (int col=0; col < 3; col++) {
//      int green=RND.nextInt(255);
//      int blue=RND.nextInt(128) + 128;
//      Led led=LedBuilder.create().color(Color.rgb(0,green,blue)).build();
//      column.add(led);
//      gridpane.add(led,col,row);
//    }
//    rows.add(column);
//  }
  
    text.setStyle("-fx-color: white");    
    text.setStyle("-fx-font: 24 arial;");    
    text.setText(Math.round(gauge.getValue()) + "");
    text.setTooltip(new Tooltip(LABEL_TOOLTIP));
    gauge.valueProperty().addListener(new ChangeListener<Number>() {
      @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
        if (newValue == null) {
          text.setText("");
          return;
        }
        text.setText((newValue.intValue()) + "");
      }
    });
    text.setPrefWidth(50);
 
    // allow the label to be clicked on to display an editable text field and have a slider movement cancel any current edit.
    text.setOnMouseClicked(newLabelEditHandler(text, gauge));
    gauge.valueProperty().addListener(new ChangeListener<Number>() {
      @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
        text.setContentDisplay(ContentDisplay.TEXT_ONLY);
      }
    });
        final IntField intField = new IntField(0, 100, 0);
        intField.setTooltip(new Tooltip(EDIT_FIELD_TOOLTIP));
        intField.valueProperty().bindBidirectional(gauge.valueProperty());
        intField.setPrefWidth(50);

        
       Group root = new Group();
       Scene scene = new Scene(root, 600, 600);
 
        
        root.setAutoSizeChildren(true);
        root.getChildren().addAll(gauge, text, intField, gridpane);
        gauge.resizeRelocate(50,175,389,26);

        text.resizeRelocate(50,50,389,26);
        intField.resizeRelocate(100,50,389,26);

        scene.setFill(Color.BLACK);

        stage.setScene(scene);
        stage.show();

        calcNoOfNodes(scene.getRoot());
        System.out.println("No. of nodes in scene: " + noOfNodes);
        timer.start();
    }

     private static void calcNoOfNodes(Node node) {
        if (node instanceof Parent) {
            if (((Parent) node).getChildrenUnmodifiable().size() != 0) {
                ObservableList<Node> tempChildren = ((Parent) node).getChildrenUnmodifiable();
                noOfNodes += tempChildren.size();
                for (Node n : tempChildren) {
                    calcNoOfNodes(n);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
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
 
  // helper text field subclass which restricts text input to a given range of natural int numbers
  // and exposes the current numeric int value of the edit box as a value property.
  class IntField extends TextField {
    final private IntegerProperty value;
    final private int minValue;
    final private int maxValue;
 
    // expose an integer value property for the text field.
    public int  getValue()                 { return value.getValue(); }
    public void setValue(int newValue)     { value.setValue(newValue); }
    public IntegerProperty valueProperty() { return value; }
    
    IntField(int minValue, int maxValue, int initialValue) {
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
      value = new SimpleIntegerProperty(initialValue);
      setText(initialValue + "");
 
      final IntField intField = this;
 
      // make sure the value property is clamped to the required range
      // and update the field's text to be in sync with the value.
      value.addListener(new ChangeListener<Number>() {
        @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
          if (newValue == null) {
            intField.setText("");
          } else {
            if (newValue.intValue() < intField.minValue) {
              value.setValue(intField.minValue);
              return;
            }
 
            if (newValue.intValue() > intField.maxValue) {
              value.setValue(intField.maxValue);
              return;
            }
 
            if (newValue.intValue() == 0 && (textProperty().get() == null || "".equals(textProperty().get()))) {
              // no action required, text property is already blank, we don't need to set it to 0.
            } else {
              intField.setText(newValue.toString());
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
 
          final int intValue = Integer.parseInt(newValue);
 
          if (intField.minValue > intValue || intValue > intField.maxValue) {
            textProperty().setValue(oldValue);
          }
          
          value.set(Integer.parseInt(textProperty().get()));
        }
      });
    }
  }}
