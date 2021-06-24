package com.mojahed.turing.logic;

import com.mojahed.turing.exception.TuringMachineException;
import com.mojahed.turing.logic.model.Move;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Ali Mojahed on 6/21/2021
 * @project turing-machine
 **/

@Log4j2
public class TuringMachineCommandLineApp implements Serializable {
    public static void main(String[] args) throws TuringMachineException {
        TuringMachine turingMachine = new TuringMachine();
        turingMachine.addAlphabet("a b");
        turingMachine.addTapeAlphabet(" a ,, b c");
        turingMachine.addStates(3);
        turingMachine.setInitialState("0");
        turingMachine.setInitialState("q0");
        turingMachine.setFinalStates(Arrays.asList("2"));
        turingMachine.addTransitionState("q0", 'b', "q1", 'b', Move.RIGHT);
        turingMachine.addTransitionState("q1", 'b', "q0", 'b', Move.LEFT);
//        turingMachine.addTransitionState("0", 'a', "q1", 'a', Move.RIGHT);
//        turingMachine.addTransitionState("1", 'a', "q2", 'a', Move.RIGHT);

        turingMachine.startAcceptor("bb");
        log.info("MACHINE OUTPUT: " + turingMachine.acceptAtOnce());

    }
}




//    Scanner scanner = new Scanner(System.in);
//
//    TuringMachine turingMachine = new TuringMachine();
//
//        log.info("START");
//                log.info("please enter alphabet (separated with comma or space): ");
//                String alphabet = scanner.nextLine();
//                alphabet = alphabet.replaceAll("[, ]", "");
//                turingMachine.addAlphabet(alphabet);
//
//                log.info("please enter tape alphabet (separated with comma or space): ");
//                String tapeAlphabet = scanner.nextLine();
//                tapeAlphabet = tapeAlphabet.replaceAll("[, ]", "");
//                turingMachine.addTapeAlphabet(tapeAlphabet);
//
//                log.info("please enter number of states (just one number): ");
//                int numberOfStates = Integer.parseInt(scanner.nextLine());
//                turingMachine.addStates(numberOfStates);
