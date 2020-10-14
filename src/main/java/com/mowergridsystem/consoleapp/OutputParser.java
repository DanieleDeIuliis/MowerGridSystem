package com.mowergridsystem.consoleapp;

import com.mowergridsystem.model.Mower;
import com.mowergridsystem.model.MowerManager;
import com.mowergridsystem.model.Position;

import java.util.List;

public class OutputParser {

    public String computeOutputString(List<MowerManager> managers){
        StringBuilder outputBuilder = new StringBuilder();
        int maxRowIndex = managers.get(0).getGrid().getRowsNumber() - 1;
        for(MowerManager manager : managers){
            Mower currentMower = manager.getMower();
            Position currentPosition = currentMower.getPosition();
            outputBuilder.append(currentPosition.getColumnCoordinate()).append(" ")
                    .append(maxRowIndex - currentPosition.getRowCoordinate()).append(" ")
                    .append(currentMower.getCurrentOrientation()).append("\n");
        }
        return outputBuilder.toString().trim();
    }
}
