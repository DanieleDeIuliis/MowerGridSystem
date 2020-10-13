package com.mowergridsystem.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import static com.mowergridsystem.model.OrientationEnum.*;

@NoArgsConstructor
@Data
public class StandardMower implements Mower{

    private static final int MAX_ORIENTATIONS_SIZE = 4;
    private static final OrientationEnum[] orderedOrientations = {NORTH, EST, SUD, WEST};
    private int currentOrientationIndex;
    private Position position;

    StandardMower(Position position, OrientationEnum orientation){
        this.position = position;
        this.currentOrientationIndex = convertOrientationToIndex(orientation);
    }

//    @Override
//    public void move() {
//        OrientationEnum currentOrientation = getCurrentOrientation();
//        int amountToMove = currentOrientation.getValue();
//        if(isOrientationHorizontal(currentOrientation))
//            moveHorizontally(amountToMove);
//        else
//            moveVertically(amountToMove);
//    }

    @Override
    public void moveHorizontally(){
        OrientationEnum currentOrientation = getCurrentOrientation();
        int newCoordinate = position.getXCoordinate() + currentOrientation.getValue();
        position.setXCoordinate(newCoordinate);
    }

    @Override
    public void moveVertically(){
        OrientationEnum currentOrientation = getCurrentOrientation();
        int newCoordinate = position.getYCoordinate() + currentOrientation.getValue();
        position.setYCoordinate(newCoordinate);
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


    public boolean isOrientationHorizontal(OrientationEnum currentOrientation){
        return currentOrientation == EST || currentOrientation == WEST;
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
