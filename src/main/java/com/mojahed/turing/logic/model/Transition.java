package com.mojahed.turing.logic.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Ali Mojahed on 6/21/2021
 * @project turing-machine
 **/

@Getter
@Setter
@Builder
@ToString
public class Transition implements Serializable {
    @ToString.Exclude
    private State nextState;
    private Character toWrite;
    private Move move;
}
