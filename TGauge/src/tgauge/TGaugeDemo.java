/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tgauge;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tgauge.Section;
import tgauge.TGauge;

import java.util.Random;


/**
 *
 * @author Gerrit Grunwald <han.solo.gg at gmail.com>
 */
public class TGaugeDemo extends Application {
    private static final Random RND = new Random();
    private static int          noOfNodes = 0;
    private Section[]           sections;
    private TGauge              gauge;
    private long                lastTimerCall;
    private AnimationTimer      timer;

    @Override public void init() {
        sections = new Section[] {
            new Section(0, 80, Color.rgb(64, 182, 75)),
//            new Section(16, 20, Color.rgb(64, 182, 75)),
//            new Section(20, 24, Color.rgb(64, 182, 75)),
            new Section(80, 90, Color.rgb(209, 184, 74)),
            new Section(90, 100, Color.rgb(209, 78, 74))
        };
        gauge = new TGauge();
        gauge.setMinValue(0);
        gauge.setMaxValue(100);
        gauge.setThreshold(90);
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
    }

    @Override public void start(Stage stage) {
        StackPane pane = new StackPane();
        pane.getChildren().add(gauge);

        Scene scene = new Scene(pane);

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
}
