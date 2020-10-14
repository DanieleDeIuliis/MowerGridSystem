package com.mowergridsystem.model;

public enum CommandEnum {
    L(-1),
    R(1),
    F(0);

    private final int value;
    private CommandEnum(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
