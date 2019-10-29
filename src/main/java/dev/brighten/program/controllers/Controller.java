package dev.brighten.program.controllers;


import dev.brighten.program.Run;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public ComboBox hashType;

    @FXML
    public TextField filePath;

    @FXML
    public TextField result;

    @FXML
    public Button runButton;

    @FXML
    public Text completeTimeText;

    public static Controller INSTANCE;

    public Controller() {
        INSTANCE = this;
    }

    @FXML
    public void onRunPress(ActionEvent actionEvent) {
        if(filePath.getText() != null && filePath.getText().length() > 0) {
            try {
                String checkSum = Run.INSTANCE.getCheckSum(
                        (String)hashType
                        .getSelectionModel()
                        .getSelectedItem());

                result.setDisable(false);
                result.setText(checkSum);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hashType.getItems().addAll("CRC", "SHA-1", "SHA-256", "MD5");
    }

    @FXML
    public void onDragDropped(DragEvent dragEvent) {
        Dragboard db = dragEvent.getDragboard();

        if(db.hasFiles()) {
            String string = db.getFiles().get(0).toString();
            filePath.setText(string);
            runButton.setDisable(false);
            dragEvent.setDropCompleted(true);
        }
    }

    @FXML
    public void onDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            /* allow for both copying and moving, whatever user chooses */
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }
}
