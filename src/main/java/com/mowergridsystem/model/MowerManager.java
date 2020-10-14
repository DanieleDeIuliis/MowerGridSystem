package com.mowergridsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Queue;
import java.util.function.BiPredicate;

import static com.mowergridsystem.model.CommandEnum.*;

@AllArgsConstructor
public class MowerManager {
    @Getter
    private Mower mower;
    @Getter
    private Queue<CommandEnum> commands;
    @Getter
    private Grid grid;

    public boolean executeNextCommand(){
        boolean commandSuccess;
        CommandEnum command = commands.poll();
        if(command == F){
            commandSuccess = moveMower();
        }else{
            commandSuccess = changeMowerOrientation(command);
        }
        return commandSuccess;
    }

    private boolean moveMower() {
        OrientationEnum currentOrientation = mower.getCurrentOrientation();
        int amountToMove = currentOrientation.getValue();
        int rowIndex = mower.getPosition().getRowCoordinate();
        int columnIndex = mower.getPosition().getColumnCoordinate();
        switch (currentOrientation) {
            case N, S -> rowIndex += amountToMove;
            case E, W -> columnIndex += amountToMove;
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
