package com.mowergridsystem.model;

/**
 * Enum representing the four possible orientations:
 * N = north
 * E = est
 * S = sud
 * W = west
 * The values represent the difference in the corresponding index that a move forward would cause
 * using a "top left notation (0,0)". (e.g. position(1,1) N -> position(0,1) N)
 */
public enum OrientationEnum {
    N(-1),
    E(1),
    S(1),
    W(-1);

    private final int value;
    OrientationEnum(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public static boolean contains(String value){
        for(OrientationEnum orientation : OrientationEnum.values()){
            if(orientation.name().equals(value))
                return true;
        }
        return false;
    }
}
