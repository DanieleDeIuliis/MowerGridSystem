package com.mowergridsystem.model;

import lombok.Data;

import static com.mowergridsystem.model.OrientationEnum.*;

@Data
public class StandardMower implements Mower{

    private static final int MAX_ORIENTATIONS_SIZE = 4;
    private static final OrientationEnum[] orderedOrientations = {N, E, S, W};
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
        return orderedOrientations[currentOrientationIndex];
    }

    private int convertOrientationToIndex(OrientationEnum orientation){
        int index = 0;
        for(int i = 0; i < orderedOrientations.length; i++){
            if(orderedOrientations[i] == orientation)
                index = i;
        }
        return index;
    }


}
