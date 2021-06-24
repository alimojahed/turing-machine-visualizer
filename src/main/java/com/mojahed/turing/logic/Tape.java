package com.mojahed.turing.logic;

import com.mojahed.turing.logic.model.State;
import com.mojahed.turing.logic.model.TapeElement;
import com.mojahed.turing.logic.util.DoublyLikedList;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

/**
 * @author Ali Mojahed on 6/21/2021
 * @project turing-machine
 **/

@Log4j2
public class Tape implements Serializable {
    private DoublyLikedList<TapeElement> tape = new DoublyLikedList<>();

    public Tape(State initialState, String input) {
        initializeTape(initialState, input);

    }

    private void initializeTape(State initialState, String input) {

        tape.addFirst(new TapeElement(initialState, input.charAt(0)));

        for (int i=1; i<input.toCharArray().length; i++){
            tape.addAfterCurrent(new TapeElement(input.charAt(i)));
            tape.goNext();
        }

        tape.goToFirst();


    }


    public void writeAndGoRight(State nextState, Character toWrite) {
        writeAndReset(toWrite);

        if (!tape.hasNext()) {
            tape.addAfterCurrent(new TapeElement(nextState, TuringMachine.BLANK));
        }
        tape.goNext();
        tape.getCurrent().setCurrentState(nextState);

    }

    public void writeAndGoLeft(State nextState, Character toWrite) {
        writeAndReset(toWrite);

        if (!tape.hasPrevious()) {
            tape.addBeforeCurrent(new TapeElement(nextState, TuringMachine.BLANK));
        }
        tape.goPrevious();
        tape.getCurrent().setCurrentState(nextState);
    }

    public TapeElement getCurrentState() {
        return tape.getCurrent();
    }

    private void writeAndReset(Character toWrite) {
        tape.getCurrent().setData(toWrite);
        tape.getCurrent().setCurrentState(null);
    }

    @Override
    public String toString() {
        DoublyLikedList<TapeElement> tapeCopy = (DoublyLikedList<TapeElement>)  SerializationUtils.clone(tape);
        if (tapeCopy.isEmpty()) {
            return "[]";
        }
        tapeCopy.goToFirst();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");

        do {
            TapeElement current = tapeCopy.getCurrent();

            stringBuilder.append(current.toString());

            tapeCopy.goNext();

        } while (tapeCopy.hasNext());

        stringBuilder.append(tapeCopy.getCurrent().toString());

        stringBuilder.append(")⊢");

        return stringBuilder.toString();
    }


    public DoublyLikedList<TapeElement> getTape() {
        return tape;
    }


}
