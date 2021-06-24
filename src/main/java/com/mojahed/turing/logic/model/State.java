package com.mojahed.turing.logic.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Ali Mojahed on 6/21/2021
 * @project turing-machine
 **/

@Getter
@Setter
@ToString
public class State implements Serializable {
    private String name;
    private int id;
    private Kind kind = Kind.NORMAL;
    private Map<Character, Transition> transitionMap = new HashMap<>();

    public State(String name, int id) {
        this.name = name;
        this.id = id;
    }


    public void addTransition(char c, Transition transition) {
        transitionMap.put(c, transition);
    }

    public boolean hasTransition(char c) {
        return transitionMap.getOrDefault(c, null) == null;
    }

    public Transition getTransition(char on){
        return transitionMap.getOrDefault(on, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return id == state.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
