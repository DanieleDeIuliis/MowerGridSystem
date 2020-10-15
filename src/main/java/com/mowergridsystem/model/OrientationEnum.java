package com.mowergridsystem.model;

public enum OrientationEnum {
    N(-1),
    E(1),
    S(1),
    W(-1);

    private final int value;
    private OrientationEnum(int value){
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
