package com.mowergridsystem.consoleapp;

import com.mowergridsystem.exceptions.BadInputFormatException;
import com.mowergridsystem.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class InputParser {

    public static final int GRID_DIMENSIONS = 2;
    public static final int COORDINATES_SIZE = 3;
    private int gridRowSize;
    private int gridColumnSize;

    public List<MowerManager> parseInputFromFile(File input)
            throws FileNotFoundException, BadInputFormatException {
            List<MowerManager> mowerManagers = new ArrayList<>();
            Scanner scanInput;
            try {
                scanInput = new Scanner(input);
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException("Input file not found. Insert an existing file name");
            }
            String gridBounds = scanInput.hasNextLine() ? scanInput.nextLine() : "";
            Grid grid = createGridFromInputLine(gridBounds);
            while(scanInput.hasNextLine()){
                String mowerCoordinates = scanInput.nextLine().trim();
                String mowerCommands = scanInput.hasNextLine() ? scanInput.nextLine().trim() : "";
                try {
                    mowerManagers.add(createMowerManagerFromInput(mowerCoordinates, mowerCommands, grid));
                }catch(BadInputFormatException e){
                 //LOG
                }
            }
            return mowerManagers;
    }

    private MowerManager createMowerManagerFromInput(String mowerCoordinates, String mowerCommands,
                                                     Grid grid)
            throws BadInputFormatException {
        if(isMowerInputValid(mowerCoordinates ,mowerCommands, grid)){
            Mower mower = createMowerFromInput(mowerCoordinates);
            grid.invertPositionIsOccupiedState(mower.getPosition());
            Queue<CommandEnum> commands = createCommandsQueue(mowerCommands);
            return new MowerManager(mower, commands, grid);
        }else{
            throw new BadInputFormatException();
        }
    }

    private boolean isMowerInputValid(String mowerCoordinates, String mowerCommands, Grid grid) {
        return areMowerCoordinatesValid(mowerCoordinates, grid) && areMowerCommandsValid(mowerCommands);
    }

    private boolean areMowerCommandsValid(String mowerCommands) {
        if(mowerCommands.length() == 0)
            return false;
        for(char command : mowerCommands.toCharArray()){
            if(!CommandEnum.contains(String.valueOf(command)))
                return false;
        }
        return true;
    }

    private boolean areMowerCoordinatesValid(String mowerCoordinates, Grid grid) {
        for(int i = 0; i < mowerCoordinates.length() - 1; i++){
            if(Character.isLetter(mowerCoordinates.charAt(i)))
                return false;
        }
        String[] coordinates = mowerCoordinates.split(" ");
        if(coordinates.length == COORDINATES_SIZE){
            int rowValue = convertRowIndex(coordinates[1]);
            int columnValue = Integer.parseInt(coordinates[0]);
            String orientation = coordinates[2];
            return grid.isNewPositionValid(new Position(rowValue, columnValue)) &&
                    OrientationEnum.contains(orientation);
        }
        return false;
    }

    private Queue<CommandEnum> createCommandsQueue(String mowerCommands) {
        Queue<CommandEnum> commands = new LinkedList<>();
        for(char c : mowerCommands.toCharArray()){
            commands.add(CommandEnum.valueOf(String.valueOf(c)));
        }
        return commands;
    }

    private Mower createMowerFromInput(String mowerCoordinates){
        String[] positionEntries = mowerCoordinates.split(" ");
        String rowIndex = positionEntries[1];
        String columnIndex = positionEntries[0];
        Position position = createPosition(rowIndex, columnIndex);
        OrientationEnum orientation = OrientationEnum.valueOf(positionEntries[2]);
        return new StandardMower(position, orientation);
    }

    private Position createPosition(String rowIndex, String columnIndex) {
        int rowIndexValue = convertRowIndex(rowIndex);
        int columnIndexValue = Integer.parseInt(columnIndex);
        return new Position(rowIndexValue, columnIndexValue);
    }

    private int convertRowIndex(String rowIndex) {
        return (gridRowSize - 1) - Integer.parseInt(rowIndex);
    }

    private Grid createGridFromInputLine(String gridBounds)
            throws BadInputFormatException {
        if(isGridInputValid(gridBounds)){
            int[] bounds = Arrays.stream(gridBounds.split(" "))
                    .mapToInt(Integer::parseInt).toArray();
            gridRowSize = bounds[1] + 1;
            gridColumnSize = bounds[0] + 1;
            return new Grid(gridRowSize, gridColumnSize);
        }else{
            throw new BadInputFormatException();
        }
    }

    private boolean isGridInputValid(String gridBounds){
        if(gridBounds.length() == 0)
            return false;
        for(char c : gridBounds.toCharArray()){
            if(Character.isLetter(c))
                return false;
        }
        int[] bounds = Arrays.stream(gridBounds.split(" "))
                .mapToInt(Integer::parseInt).toArray();
        return bounds.length == GRID_DIMENSIONS && bounds[0] > 0 && bounds[1] > 0;
    }

}
