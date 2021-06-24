package com.mojahed.turing.ui;

import com.mojahed.turing.exception.TuringMachineException;
import com.mojahed.turing.logic.TuringMachine;
import com.mojahed.turing.logic.model.Move;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @author Ali Mojahed on 6/21/2021
 * @project turing-machine
 **/

@Log4j2
public class AddTransitionController implements Initializable {
    public static TuringMachine turingMachine;


    public ComboBox currentStateComboBox;
    public ComboBox currentInputComboBox;
    public ComboBox nextStateComboBox;
    public ComboBox toWriteComboBox;
    public ComboBox moveComboBox;
    public Button addButton;
    public Button constructButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Integer> stateIds = new ArrayList<>(turingMachine.getInternalStates().keySet());
        Collections.sort(stateIds);

        List<String> moves = Arrays.asList("RIGHT", "LEFT");
        List<Character> tapeAlphabet = new ArrayList<>(turingMachine.getTapeAlphabet());

        currentStateComboBox.setItems(FXCollections.observableArrayList(stateIds));
        nextStateComboBox.setItems(FXCollections.observableArrayList(stateIds));

        currentInputComboBox.setItems(FXCollections.observableArrayList(tapeAlphabet));
        toWriteComboBox.setItems(FXCollections.observableArrayList(tapeAlphabet));

        moveComboBox.setItems(FXCollections.observableArrayList(moves));

        addButton.setOnAction(event -> addButtonPressedHandler());
        constructButton.setOnAction(event -> constructButtonPressedHandler());

    }

    private void constructButtonPressedHandler() {
        if (!turingMachine.hasTransition()) {
            return;
        }

        Parent root;
        try {
            MachineInputController.turingMachine = turingMachine;
            root = FXMLLoader.load(getClass().getClassLoader().getResource("MachineInput.fxml"));
            TuringMachineApplicationUI.stage.setScene(new Scene(root));
        } catch (IOException e) {
            log.error(e);
        }

    }

    private void addButtonPressedHandler() {
        if (currentStateComboBox.getValue() == null || nextStateComboBox.getValue() == null ||
                currentInputComboBox.getValue() == null || toWriteComboBox.getValue() == null ||
                moveComboBox.getValue() == null) {
            return;
        }

        try {

            turingMachine.addTransitionState(String.valueOf(currentStateComboBox.getValue()),
                    (char) currentInputComboBox.getValue(), String.valueOf(nextStateComboBox.getValue()),
                    (char) toWriteComboBox.getValue(), Move.valueOf((String) moveComboBox.getValue()));
        } catch (TuringMachineException e) {
            log.error("SOME THING IS WRONG " + e.getStatus().getDescription());
        }

        currentStateComboBox.getSelectionModel().clearSelection();
        currentInputComboBox.getSelectionModel().clearSelection();
        nextStateComboBox.getSelectionModel().clearSelection();
        toWriteComboBox.getSelectionModel().clearSelection();
        moveComboBox.getSelectionModel().clearSelection();


    }
}
