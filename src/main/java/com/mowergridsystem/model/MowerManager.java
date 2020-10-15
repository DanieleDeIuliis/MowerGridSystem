package com.mowergridsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Queue;

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

    /**
     * Takes the next command and executes it. It assumes that if the method
     * is called, there at least one command in queue.
     * @return whether the command has been successfully executed
     */
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

    /**
     * Checks if the mower can move forward based on its orientation and moves it
     * when possible.
     * @return whether the mower moved to the new position.
     */
    private boolean moveMower() {
        OrientationEnum currentOrientation = mower.getCurrentOrientation();
        int amountToMove = currentOrientation.getValue();
        Position currentPosition = mower.getPosition();
        Position nextPosition = computeNewPosition(currentOrientation, amountToMove, currentPosition);
        if(grid.checkPositionAndChangeState(currentPosition, nextPosition)){
            mower.move(nextPosition);
            log.debug("Mower {} moved from position: {}, to {}.", mower.toString(),
                    currentPosition.toString(), nextPosition.toString());
            return true;
        }
        log.debug("Mower {} stands still.");
        return false;
    }

    private Position computeNewPosition(OrientationEnum currentOrientation, int amountToMove, Position currentPosition) {
        int rowIndex = currentPosition.getRowCoordinate();
        int columnIndex = currentPosition.getColumnCoordinate();
        switch (currentOrientation) {
            case N, S -> rowIndex += amountToMove;
            case E, W -> columnIndex += amountToMove;
        }
        Position nextPosition = new Position(rowIndex, columnIndex);
        return nextPosition;
    }

    /**
     * Changes the orientation of the mower. This command can't fail
     * @param direction where to change the orientation
     * @return always return true. It's always possible to change orientation
     */
    private boolean changeMowerOrientation(CommandEnum direction) {
        mower.changeOrientation(direction);
        log.debug("Mower {} changed orientation to: {}", mower.toString(), direction.name());
        return true;
    }


}
