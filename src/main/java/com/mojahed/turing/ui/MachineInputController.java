package com.mojahed.turing.ui;

import com.mojahed.turing.exception.TuringMachineException;
import com.mojahed.turing.logic.TuringMachine;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Ali Mojahed on 6/21/2021
 * @project turing-machine
 **/


@Log4j2
public class MachineInputController implements Initializable {
    public static TuringMachine turingMachine;

    public TextField inputField;
    public Button startButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startButton.setOnAction(event -> startButtonPressedHandler());
    }

    private void startButtonPressedHandler() {
        if (inputField.getText().isEmpty() || inputField.getText().trim().isEmpty()){
            return;
        }

        try {
            turingMachine.startAcceptor(inputField.getText());

            StepByStepRoutineController.turingMachine = turingMachine;
            StepByStepRoutineController.input = inputField.getText();

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("StepByStepRoutineController.fxml"));
            TuringMachineApplicationUI.stage.setScene(new Scene(root));

        } catch (TuringMachineException e) {
            log.error("SOME THING IS WRONG " + e.getStatus().getDescription());
        } catch (IOException e) {
            log.error(e);
        }


    }
}
