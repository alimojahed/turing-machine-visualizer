package com.mojahed.turing.exception;

/**
 * @author Ali Mojahed on 6/21/2021
 * @project turing-machine
 **/

public class TuringMachineException extends Exception{
    private int code;
    private String developerMessage;
    private TuringMachineExceptionStatus status;
    public TuringMachineException(TuringMachineExceptionStatus status) {
        this.status = status;

    }

    public int getCode() {
        return code;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public TuringMachineExceptionStatus getStatus() {
        return status;
    }
}
