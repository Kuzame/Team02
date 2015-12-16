/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FanManager.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ConsoleController implements Initializable {
    static ObservableList<String> consoleObserve = observableList(FanManager.model.FanPane.outputList);
    static int i = 0;
    boolean isConsoleOutOn = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void handleConsoleOut() throws IOException {
        consolePrint();
        if (!isConsoleOutOn) {
            ConsoleController.addOutput("Console Started");
            this.consolePrint();
            isConsoleOutOn = true;
        }
    }

    @FXML
    private TextArea consoleOut;
    @FXML
    private TextArea consoleIn;

    public void consolePrint() {
        consoleObserve.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                if (!consoleObserve.isEmpty()) {
                    for (i = i; i < consoleObserve.size(); i++) {
                        consoleOut.appendText("\n");
                        consoleOut.appendText(consoleObserve.get(i));
                    }
                }
            }
        });
    }

    public ObservableList getListOut() {
        return ConsoleController.consoleObserve;
    }

    public synchronized static void addOutput(String string) {
        consoleObserve.add(string);
    }

}

