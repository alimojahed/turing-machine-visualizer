package com.mojahed.turing.logic;

import java.io.Serializable;

/**
 * @author Ali Mojahed on 6/21/2021
 * @project turing-machine
 **/

public enum MachineState implements Serializable {
    ACCEPT, PROCESSING, NOT_ACCEPT , INFINITE_LOOP, NOT_STARTED
}
