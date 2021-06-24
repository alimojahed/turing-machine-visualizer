package com.mojahed.turing.ui;

import com.mojahed.turing.exception.TuringMachineException;
import com.mojahed.turing.logic.TuringMachine;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @author Ali Mojahed on 6/21/2021
 * @project turing-machine
 **/

@Log4j2
public class InitialAndFinalStateController implements Initializable {
    public static TuringMachine turingMachine;

    public ComboBox initialStateComboBox;
    public TextField finalStates;
    public Button nextButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nextButton.setOnAction(event -> nextButtonPressedHandler());

        List<Integer> stateIds = new ArrayList<>(turingMachine.getInternalStates().keySet());
        Collections.sort(stateIds);

        initialStateComboBox.setItems(FXCollections.observableArrayList(stateIds));

    }

    private void nextButtonPressedHandler() {
        if (initialStateComboBox.getValue() == null) {
            return;
        }

        if (finalStates.getText().isEmpty() || finalStates.getText().trim().isEmpty()) {
            return;
        }

        try {


            turingMachine.setInitialState(String.valueOf(initialStateComboBox.getValue()));

            String rawFinalStates = finalStates.getText().trim();
            rawFinalStates = rawFinalStates.replaceAll("[, ]", " ");

            List<String> finalStates = Arrays.asList(rawFinalStates.split(" "));

            turingMachine.setFinalStates(finalStates);

            AddTransitionController.turingMachine = turingMachine;
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("AddTransition.fxml"));
            TuringMachineApplicationUI.stage.setScene(new Scene(root));


        } catch (TuringMachineException e) {
            log.error("SOME THING IS WRONG " + e.getStatus().getDescription());

        } catch (IOException e) {
            log.error(e);
        }
    }
}
