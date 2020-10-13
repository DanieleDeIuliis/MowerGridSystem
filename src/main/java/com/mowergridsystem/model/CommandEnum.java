package com.mowergridsystem.model;

public enum CommandEnum {
    LEFT(-1),
    RIGHT(1),
    FORWARD(0);

    private final int value;
    private CommandEnum(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
