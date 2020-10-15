package com.mowergridsystem.model;

import lombok.Data;

import static com.mowergridsystem.model.OrientationEnum.*;

@Data
public class StandardMower implements Mower{

    private static final int MAX_ORIENTATIONS_SIZE = 4;
    private static final OrientationEnum[] ORDERED_ORIENTATIONS = {N, E, S, W};
    private int currentOrientationIndex;
    private Position position;

    public StandardMower(Position position, OrientationEnum orientation){
        this.position = position;
        this.currentOrientationIndex = convertOrientationToIndex(orientation);
    }

    @Override
    public void move(Position position) {
        this.position = position;
    }

    /**
     * Uses the value of the direction (L = -1, R = +1) to navigate the constant
     * circular array ORDERED_ORIENTATIONS to compute the next one.
     * @param direction Represents where to change the orientation.
     */
    @Override
    public void changeOrientation(CommandEnum direction) {
        int newIndex = currentOrientationIndex + direction.getValue();
        if(newIndex < 0)
            newIndex = (newIndex + MAX_ORIENTATIONS_SIZE) % MAX_ORIENTATIONS_SIZE;
        else
            newIndex %= MAX_ORIENTATIONS_SIZE;
        currentOrientationIndex = newIndex;
    }

    @Override
    public OrientationEnum getCurrentOrientation(){
        return ORDERED_ORIENTATIONS[currentOrientationIndex];
    }

    /**
     *
     * @param orientation the orientation of the mower
     * @return the corresponding index in ORDERED_ORIENTATIONS
     */
    private int convertOrientationToIndex(OrientationEnum orientation){
        int index = 0;
        for(int i = 0; i < ORDERED_ORIENTATIONS.length; i++){
            if(ORDERED_ORIENTATIONS[i] == orientation)
                index = i;
        }
        return index;
    }


}
