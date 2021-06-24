package com.mojahed.turing.ui;

import com.mojahed.turing.logic.MachineState;
import com.mojahed.turing.logic.TuringMachine;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Ali Mojahed on 6/21/2021
 * @project turing-machine
 **/

@Log4j2
public class StepByStepRoutineController implements Initializable {
    public static TuringMachine turingMachine;
    public static String input;
    public Label inputStringLabel;
    public VBox tapeStateContainer;
    public Button nextStateButton;
    public Button finishButton;
    public Button anotherInputButton;
    public Label machineStateLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tapeStateContainer.getChildren().removeAll();
        tapeStateContainer.getChildren().add(new Label(turingMachine.getTape().toString()));

        inputStringLabel.setText(input);

        machineStateLabel.setText(MachineState.PROCESSING.name());

        anotherInputButton.setOnAction(event -> goToInputPage());

        nextStateButton.setOnAction(event -> nextStateButtonPressedHandler());

        finishButton.setOnAction(event -> finishButtonPressedHandler());

    }

    private void finishButtonPressedHandler() {
        MachineState state = turingMachine.acceptAtOnce();

        if (state != MachineState.PROCESSING) {
            nextStateButton.setDisable(true);
            finishButton.setDisable(true);
        }
        machineStateLabel.setText(state.name());

        for (String str: turingMachine.getTapeStateFromNow()){
            tapeStateContainer.getChildren().add(new Label(str));
        }
    }

    private void nextStateButtonPressedHandler() {
        MachineState state = turingMachine.runNext();

        if (state != MachineState.PROCESSING) {
            nextStateButton.setDisable(true);
            finishButton.setDisable(true);
        }

        tapeStateContainer.getChildren().add(new Label(turingMachine.getTape().toString()));
        machineStateLabel.setText(state.name());
    }

    private void goToInputPage() {
        try {

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MachineInput.fxml"));
            TuringMachineApplicationUI.stage.setScene(new Scene(root));

        } catch (IOException e) {
            log.error(e);
        }
    }
}
