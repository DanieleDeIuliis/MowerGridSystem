package com.mowergridsystem.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Queue;
import java.util.function.BiPredicate;

import static com.mowergridsystem.model.CommandEnum.*;

@AllArgsConstructor
@NoArgsConstructor
public class MowerManager {

    private Mower mower;
    private Queue<CommandEnum> commands;
    private Grid grid;

    public boolean executeNextCommand(){
        boolean commandSuccess;
        CommandEnum command = commands.poll();
        if(command == FORWARD){
            commandSuccess = moveMower();
        }else{
            commandSuccess = changeMowerOrientation(command);
        }
        return commandSuccess;
    }

    public Mower getMower() {
        return mower;
    }

    public Queue<CommandEnum> getCommands() {
        return commands;
    }

    public Grid getGrid() {
        return grid;
    }

    private boolean moveMower() {
        OrientationEnum currentOrientation = mower.getCurrentOrientation();
        int amountToMove = currentOrientation.getValue();
        int rowIndex = mower.getPosition().getRowCoordinate();
        int columnIndex = mower.getPosition().getColumnCoordinate();
        switch (currentOrientation) {
            case NORTH, SUD -> rowIndex += amountToMove;
            case EST, WEST -> columnIndex += amountToMove;
        }
        Position nextPosition = new Position(rowIndex, columnIndex);
        if(isNextPositionValid.test(nextPosition, grid)){
            mower.move(nextPosition);
            return true;
        }
        return false;
    }

    private boolean changeMowerOrientation(CommandEnum direction) {
        mower.changeOrientation(direction);
        return true;
    }

    private final BiPredicate<Position, Grid> isPositionInsideBoard = (p, g) ->
            ( p.getRowCoordinate() >= 0 && p.getRowCoordinate() < g.getRowsNumber()) &&
            (p.getColumnCoordinate() >= 0 && p.getColumnCoordinate() < g.getColumnsNumber());
    private final BiPredicate<Position, Grid> isNextPositionValid = (p,g) -> isPositionInsideBoard.test(p,g) &&
            !g.isCellOccupied(p);


}
