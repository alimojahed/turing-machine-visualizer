package com.mojahed.turing.logic;

import com.mojahed.turing.exception.TuringMachineException;
import com.mojahed.turing.exception.TuringMachineExceptionStatus;
import com.mojahed.turing.logic.model.*;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ali Mojahed on 6/21/2021
 * @project turing-machine
 **/

@NoArgsConstructor
@Log4j2
public class TuringMachine implements Serializable {
    public static final char BLANK = '⊡';
    public static final String STATES_PREFIX = "q";
    private static final int LOOP_DETECTOR_THRESHOLD = 100_000;

    private Map<Integer, State> internalStates = new HashMap<>();
    private Set<Character> alphabet = new HashSet<>();
    private Set<Character> tapeAlphabet = new HashSet<>();
    private State initialState;
    private Set<State> finalStates = new HashSet<>();
    private List<String> tapeStateFromNow = new ArrayList<>();
    private Tape tape;
    private MachineState machineState = MachineState.NOT_STARTED;

    private int numberOfStates = 0;
    private int loopDetectorCounter = 0;
    private boolean hasTransition = false;


    public void startAcceptor(String input) throws TuringMachineException {
        validateAcceptorInput(input);

        machineState = MachineState.PROCESSING;
        loopDetectorCounter = 0;

        tape = new Tape(initialState, input);
        tape.getTape().goToFirst();

        log.info("TAPE CREATED:" + tape.toString());
    }

    private void validateAcceptorInput(String input) throws TuringMachineException {
        for (char c : input.toCharArray()) {
            if (!tapeAlphabet.contains(c)) {
                throw new TuringMachineException(TuringMachineExceptionStatus.INVALID_INPUT);
            }
        }
    }

    public MachineState accept() {
        return machineState;
    }

    public MachineState runNext() {
        if (machineState != MachineState.PROCESSING) {
            return machineState;
        }

        TapeElement current = tape.getCurrentState();
        log.info(loopDetectorCounter + "- current tape element: " + current);
        State currentState = current.getCurrentState();
        char on = current.getData();

        Transition transition = currentState.getTransition(on);

        if (transition == null) {
            if (currentState.getKind() == Kind.FINAL) {
                machineState = MachineState.ACCEPT;
            } else {
                machineState = MachineState.NOT_ACCEPT;
            }
        } else {
            if (transition.getMove() == Move.RIGHT) {
                tape.writeAndGoRight(transition.getNextState(), transition.getToWrite());
            } else {
                tape.writeAndGoLeft(transition.getNextState(), transition.getToWrite());
            }
        }

        loopDetectorCounter++;

        if (loopDetectorCounter > LOOP_DETECTOR_THRESHOLD) {
            machineState = MachineState.INFINITE_LOOP;
        }

        log.info(loopDetectorCounter + ":" + tape);

        return machineState;
    }

    public MachineState acceptAtOnce()  {
//        if (tape == null) {
//            throw new TuringMachineException(TuringMachineExceptionStatus.ACCEPT_NOT_STARTED);
//        }
        while (loopDetectorCounter <= LOOP_DETECTOR_THRESHOLD && machineState == MachineState.PROCESSING) {
            runNext();
            tapeStateFromNow.add(tape.toString());
        }

        if (machineState == MachineState.PROCESSING) {
            machineState = MachineState.INFINITE_LOOP;
        }
        return machineState;
    }

    public void addStates(int numberOfStates) {
        this.numberOfStates = numberOfStates;

        internalStates = new HashMap<>();

        for (int i = 0; i < numberOfStates; i++) {
            internalStates.put(i, new State(STATES_PREFIX + i, i));
        }

        log.info(internalStates);
    }


    public void setInitialState(String identifier) throws TuringMachineException {
        if (!internalStates.isEmpty()) {
            State state = getState(identifier);
            if (state == null) {
                throw new TuringMachineException(TuringMachineExceptionStatus.INVALID_INPUT);
            }

            initialState = state;
        }

        log.info("INITIAL STATE SET: " + initialState);
        log.info("INTERNAL_STATE MAP:" + internalStates);

    }

    public void setFinalStates(List<String> identifiers) throws TuringMachineException {
        if (!internalStates.isEmpty()) {
            for (String identifier : identifiers) {
                State state = getState(identifier);

                if (state == null) {
                    throw new TuringMachineException(TuringMachineExceptionStatus.INVALID_INPUT);
                }

                state.setKind(Kind.FINAL);
                internalStates.put(getStateKey(identifier), state);
                finalStates.add(state);

                log.info("FINAL_STATE: " + state);
                log.info("FINAL_STATES SET: " + finalStates);

            }
        }
        log.info(internalStates);
    }


    public void addTransitionState(String identifier, char onInput,
                                   String nextStateIdentifier, char toWrite, Move move) throws TuringMachineException {
        if (internalStates.isEmpty()) {
            throw new TuringMachineException(TuringMachineExceptionStatus.NO_STATE_FOUND);
        }

        State state = getState(identifier);
        State nextState = getState(nextStateIdentifier);

        if (state == null || nextState == null) {
            throw new TuringMachineException(TuringMachineExceptionStatus.NO_SUCH_STATE);
        }

        if (!(tapeAlphabet.contains(onInput) && tapeAlphabet.contains(toWrite))) {
            throw new TuringMachineException(TuringMachineExceptionStatus.NO_SUCH_TAPE_ALPHABET);
        }

        Transition transition = Transition.builder()
                .toWrite(toWrite)
                .nextState(nextState)
                .move(move)
                .build();

        state.addTransition(onInput, transition);

        log.info("ADD TRANSITION TO: " + transition);

        internalStates.put(getStateKey(identifier), state);
        log.info("ADDING TRANSITIONS - INTERNAL STATES: " + internalStates);
        hasTransition = true;
    }

    private State getState(String identifier) {
        int key = getStateKey(identifier);
        return internalStates.getOrDefault(key, null);

    }

    private int getStateKey(String identifier) {
        identifier = identifier.trim();
        return Integer.parseInt(identifier.replaceAll(STATES_PREFIX, ""));
    }

    public void addAlphabet(String alphabet) throws TuringMachineException {
        alphabet = alphabet.trim();
        alphabet = alphabet.replaceAll("[, ]", "");

        validateAlphabetInput(alphabet);

        for (Character c : alphabet.toCharArray()) {
            this.alphabet.add(c);
        }

        log.info("alphabet: " + this.alphabet);

    }

    private void validateAlphabetInput(String alphabet) throws TuringMachineException {
        if (!hasVaidCharacter(alphabet)) {
            throw new TuringMachineException(TuringMachineExceptionStatus.INVALID_ALPHABET);
        }

    }

    private boolean hasVaidCharacter(String string) {
        log.info(string);
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");
        Matcher matcher = pattern.matcher(string);

        return matcher.find();
    }

    public void addTapeAlphabet(String rawTapeAlphabet) throws TuringMachineException {
        rawTapeAlphabet = rawTapeAlphabet.trim();
        rawTapeAlphabet = rawTapeAlphabet.replaceAll("[, ]", "");

        if (!rawTapeAlphabet.isEmpty()) {
            validateTapeAlphabetInput(rawTapeAlphabet);
        }

        for (Character c : rawTapeAlphabet.toCharArray()) {
            tapeAlphabet.add(c);
        }

        tapeAlphabet.add(BLANK);
        tapeAlphabet.addAll(alphabet);

        log.info("tape alphabet: " + tapeAlphabet);

    }

    private void validateTapeAlphabetInput(String rawTapeAlphabet) throws TuringMachineException {

        if (alphabet.isEmpty()) {
            throw new TuringMachineException(TuringMachineExceptionStatus.NO_ALPHABET);
        }

        if (!hasVaidCharacter(rawTapeAlphabet)) {
            throw new TuringMachineException(TuringMachineExceptionStatus.INVALID_TAPE_ALPHABET);
        }
    }

    public Tape getTape() {
        return tape;
    }

    public Map<Integer, State> getInternalStates() {
        return internalStates;
    }

    public Set<Character> getTapeAlphabet() {
        return tapeAlphabet;
    }

    public boolean hasTransition() {
        return hasTransition;
    }

    public List<String> getTapeStateFromNow(){
        return tapeStateFromNow;
    }
}
