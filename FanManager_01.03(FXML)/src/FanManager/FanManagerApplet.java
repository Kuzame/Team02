/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FanManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Reign
 */
public class FanManagerApplet extends JApplet {
    
    private static final int JFXPANEL_WIDTH_INT = 1032;
    private static final int JFXPANEL_HEIGHT_INT = 861;
    private static JFXPanel fxContainer;
    private static BorderPane rootLayout;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch (Exception e) {
                }
                
                JFrame frame = new JFrame("JavaFX 2 in Swing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                JApplet applet = new FanManagerApplet();
                applet.init();

                frame.setContentPane(applet.getContentPane());
                
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                applet.start();
            }
        });
    }
    
    @Override
    public void init() {
        fxContainer = new JFXPanel();
        fxContainer.setPreferredSize(new Dimension(JFXPANEL_WIDTH_INT, JFXPANEL_HEIGHT_INT));
        add(fxContainer, BorderLayout.CENTER);
        // create JavaFX scene
        Platform.runLater(new Runnable() {
            
            @Override
            public void run() {
                createScene();
                showFanManagerLayout();

            }
        });
    }
    
    private void createScene() {
    try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(FanManager.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            fxContainer.setScene(new Scene(rootLayout));


//            // Show the scene containing the root layout.
//            Scene scene = new Scene(rootLayout);
//            fxContainer.setScene(scene);
            //fxContainer.setVisible(true);
        } catch (IOException e) {
                       System.out.println("IOException in initRootLayout");

            e.printStackTrace();
        }    }
    
//    /**
//     * Initializes the root layout.
//     */
//    public void initRootLayout() {
//        try {
//            // Load root layout from fxml file.
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(FanManager.class.getResource("view/RootLayout.fxml"));
//            rootLayout = (BorderPane) loader.load();
//
//            // Show the scene containing the root layout.
//            Scene scene = new Scene(rootLayout);
//            fxContainer.setScene(new Scene(rootLayout));
//            
//            fxContainer.setVisible(true);
//        } catch (IOException e) {
//                       System.out.println("IOException in initRootLayout");
//
//            e.printStackTrace();
//        }
//    }
    
    
        static public void showFanManagerLayout() {
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

}
