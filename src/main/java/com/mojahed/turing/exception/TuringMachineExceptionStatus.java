package com.mojahed.turing.exception;

/**
 * @author Ali Mojahed on 6/21/2021
 * @project turing-machine
 **/

public enum TuringMachineExceptionStatus {
    INVALID_INPUT("invalid input"),
    INVALID_ALPHABET("invalid alphabet"),
    INVALID_TAPE_ALPHABET("invalid tape alphabet"),
    NO_ALPHABET("no alphabet entered"),
    NO_STATE_FOUND("first you have to entered states"),
    NO_SUCH_STATE("no such state available"),
    NO_SUCH_TAPE_ALPHABET("no such tape alphabet"),
    ACCEPT_NOT_STARTED("accept process not started yet")
    ;

    private final String description;

    TuringMachineExceptionStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
