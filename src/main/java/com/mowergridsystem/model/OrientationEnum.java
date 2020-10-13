package com.mowergridsystem.model;

public enum OrientationEnum {
    NORTH(1),
    EST(1),
    SUD(-1),
    WEST(-1);

    private final int value;
    private OrientationEnum(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
