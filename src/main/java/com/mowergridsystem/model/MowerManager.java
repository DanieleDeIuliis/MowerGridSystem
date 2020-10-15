package com.mowergridsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.function.BiPredicate;

import static com.mowergridsystem.model.CommandEnum.*;

@Slf4j
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
        Position currentPosition = mower.getPosition();
        int rowIndex = currentPosition.getRowCoordinate();
        int columnIndex = currentPosition.getColumnCoordinate();
        switch (currentOrientation) {
            case N, S -> rowIndex += amountToMove;
            case E, W -> columnIndex += amountToMove;
        }
        Position nextPosition = new Position(rowIndex, columnIndex);
        if(grid.checkPositionAndChangeState(currentPosition, nextPosition)){
            mower.move(nextPosition);
            log.debug("Mower {} moved from position: {}, to {}.", mower.toString(),
                    currentPosition.toString(), nextPosition.toString());
            return true;
        }
        log.debug("Mower {} stands still.");
        return false;
    }

    private boolean changeMowerOrientation(CommandEnum direction) {
        mower.changeOrientation(direction);
        log.debug("Mower {} changed orientation to: {}", mower.toString(), direction.name());
        return true;
    }


}
