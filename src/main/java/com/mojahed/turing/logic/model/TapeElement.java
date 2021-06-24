package com.mojahed.turing.logic.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Ali Mojahed on 6/21/2021
 * @project turing-machine
 **/

@Getter
@Setter
public class TapeElement implements Serializable {
    private State currentState;
    private Character data;

    public TapeElement(State currentState, Character data) {
        this.currentState = currentState;
        this.data = data;
    }

    public TapeElement(Character data) {
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        if (currentState != null){
            stringBuilder.append("(");
            stringBuilder.append(currentState.getName());
            stringBuilder.append(")->");
        }

        stringBuilder.append(data);

        return stringBuilder.toString();
    }
}