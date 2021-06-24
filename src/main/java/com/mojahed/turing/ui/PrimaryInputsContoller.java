package com.mojahed.turing.ui;

import com.mojahed.turing.exception.TuringMachineException;
import com.mojahed.turing.logic.TuringMachine;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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
public class PrimaryInputsContoller implements Initializable {

    public static TuringMachine turingMachine;
    public TextField alphabetTextField;
    public TextField tapeAlphabetTextField;
    public Slider stateNumberSlider;
    public Button nextButton;
    public Label sliderLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stateNumberSlider.setMin(1);
        stateNumberSlider.setMax(50);
        stateNumberSlider.setMajorTickUnit(10);
        stateNumberSlider.setBlockIncrement(1);


        nextButton.setOnAction(event -> nextButtonPressedHandler());

        stateNumberSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sliderLabel.textProperty().setValue(String.valueOf(newValue.intValue()));
            }
        });

    }

    private void nextButtonPressedHandler() {
        if (alphabetTextField.getText().isEmpty() || alphabetTextField.getText().trim().isEmpty()) {
            return;
        }
        try {
            turingMachine.addAlphabet(alphabetTextField.getText());
            turingMachine.addTapeAlphabet(tapeAlphabetTextField.getText());
            turingMachine.addStates((int) stateNumberSlider.getValue());
            InitialAndFinalStateController.turingMachine = turingMachine;
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("InitialAndFinalState.fxml"));
            TuringMachineApplicationUI.stage.setScene(new Scene(root));

        } catch (TuringMachineException e) {
            log.error("SOME THING IS WRONG " + e.getStatus().getDescription());

        } catch (IOException e) {
            log.error(e);
        }


    }

}
