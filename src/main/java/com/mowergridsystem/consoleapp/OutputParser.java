package com.mowergridsystem.consoleapp;

import com.mowergridsystem.model.Mower;
import com.mowergridsystem.model.MowerManager;
import com.mowergridsystem.model.Position;

import java.util.List;

public class OutputParser {

    /**
     * Formats the output from the correspondent managers
     * @param managers List of the managers involved.
     * @return Output string with line format: %d %d %s -> X coordinate, Y coordinate, orientation
     */
    public String computeOutputString(List<MowerManager> managers){
        StringBuilder outputBuilder = new StringBuilder();
        int maxRowIndex = managers.get(0).getGrid().getRowsNumber() - 1;
        for(MowerManager manager : managers){
            createManagerResult(outputBuilder, maxRowIndex, manager);
        }
        return outputBuilder.toString().trim();
    }

    private void createManagerResult(StringBuilder outputBuilder, int maxRowIndex, MowerManager manager) {
        Mower currentMower = manager.getMower();
        Position currentPosition = currentMower.getPosition();
        outputBuilder.append(currentPosition.getColumnCoordinate()).append(" ")
                .append(maxRowIndex - currentPosition.getRowCoordinate()).append(" ")
                .append(currentMower.getCurrentOrientation()).append("\n");
    }
}
