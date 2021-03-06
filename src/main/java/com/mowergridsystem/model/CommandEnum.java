package com.mowergridsystem.model;

/**
 * Enum to represent the three different commands supported:
 * L = change the orientation 90 degrees to the left
 * R = change the orientation 90 degrees to the right
 * F = move forward
 * The values are use to navigate a circular array of orientations.
 */
public enum CommandEnum {
    L(-1),
    R(1),
    F(0);

    private final int value;
    CommandEnum(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public static boolean contains(String value){
        for(CommandEnum command : CommandEnum.values()){
            if(command.name().equals(value))
                return true;
        }
        return false;
    }
}
