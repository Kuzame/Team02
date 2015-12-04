/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FanManager.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ConsoleController implements Initializable {

@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    
}

/*@FXML
public void append(int i) {
textArea.appendText(String.valueOf((char) i));
}*/

@FXML
private TextArea textArea;
}
