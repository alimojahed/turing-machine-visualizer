package com.mojahed.turing.ui;

import com.mojahed.turing.logic.TuringMachine;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Ali Mojahed on 6/20/2021
 * @project turing-machine
 **/

public class TuringMachineApplicationUI extends Application {

    public static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        PrimaryInputsContoller.turingMachine = new TuringMachine();

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("PrimaryInputs.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}
