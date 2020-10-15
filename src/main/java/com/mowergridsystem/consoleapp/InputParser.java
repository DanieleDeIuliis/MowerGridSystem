package com.mowergridsystem.consoleapp;

import com.mowergridsystem.exceptions.BadInputFormatException;
import com.mowergridsystem.model.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Slf4j
public class InputParser {

    public static final int GRID_DIMENSIONS = 2;
    public static final int COORDINATES_SIZE = 3;
    private int gridRowSize;

    /**
     * Parse the input and creates the grid and the necessary managers.
     * @param input Path to the input file
     * @return The list of Managers created for the set of mowers
     * @throws FileNotFoundException
     * @throws BadInputFormatException
     */
    public List<MowerManager> parseInputFromFile(File input)
            throws FileNotFoundException, BadInputFormatException {
        List<MowerManager> mowerManagers = new ArrayList<>();
        Scanner scanInput;
        try {
            scanInput = new Scanner(input);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Input file not found. Insert an existing file name");
        }
        String gridBounds = scanInput.hasNextLine() ? scanInput.nextLine().trim() : "";
        Grid grid = createGridFromInputLine(gridBounds);
        parseMowerInfo(mowerManagers, scanInput, grid);
        return mowerManagers;
    }

    private void parseMowerInfo(List<MowerManager> mowerManagers, Scanner scanInput, Grid grid) {
        while(scanInput.hasNextLine()){
            String mowerCoordinates = scanInput.nextLine().trim();
            String mowerCommands = scanInput.hasNextLine() ? scanInput.nextLine().trim() : "";
            try {
                mowerManagers.add(createMowerManagerFromInput(mowerCoordinates, mowerCommands, grid));
            }catch(BadInputFormatException e){
                log.debug("The input is not formatted as expected. The mower with coordinates: {} " +
                                "and commands: {} has been skipped.",
                        mowerCoordinates, mowerCommands);
            }
        }
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
            if(!CommandEnum.contains(String.valueOf(command))){
                log.debug("Mower commands: {} not valid.", mowerCommands);
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether the coordinates passed are two numbers and an orientation. It also checks
     * if the position corresponding to the coordinates is valid (the cell is not occupied).
     * @param mowerCoordinates format: %d %d %s -> X coordinate, Y coordinate, orientation
     * @param grid
     * @return The validity of mowerCoordinates as input string
     */
    private boolean areMowerCoordinatesValid(String mowerCoordinates, Grid grid) {
        if (isLetterPresent(mowerCoordinates)) return false;
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

    /**
     * Checks whether there are letter in the coordinates range of the input line (0, line.length -1)
     */
    private boolean isLetterPresent(String mowerCoordinates) {
        for(int i = 0; i < mowerCoordinates.length() - 1; i++){
            if(Character.isLetter(mowerCoordinates.charAt(i))){
                log.debug("Letter {} found as mower coordinate.", mowerCoordinates.charAt(i));
                return true;
            }
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

    /**
     * Converts the rowIndex to the proper notation
     * @param rowIndex Row index in "bottom left notation" ( bottom left cell: 0,0)
     * @return rowIndex in "top left notation" (top left cell: 0,0)
     */
    private int convertRowIndex(String rowIndex) {
        return (gridRowSize - 1) - Integer.parseInt(rowIndex);
    }

    private Grid createGridFromInputLine(String gridBounds)
            throws BadInputFormatException {
        if(isGridInputValid(gridBounds)){
            int[] bounds = Arrays.stream(gridBounds.split(" "))
                    .mapToInt(Integer::parseInt).toArray();
            gridRowSize = bounds[1] + 1;
            int gridColumnSize = bounds[0] + 1;
            return new Grid(gridRowSize, gridColumnSize);
        }else{
            log.debug("Grid bounds: {} are not valid.", gridBounds);
            throw new BadInputFormatException();
        }
    }

    /**
     * Checks whether there are letters as coordinates and if at least one of them
     * is greater than zero (single row/column grid).
     * @param gridBounds Grid top right in input coordinates
     * @return The validity of gridBounds as input line
     */
    private boolean isGridInputValid(String gridBounds){
        if(gridBounds.length() == 0)
            return false;
        for(char c : gridBounds.toCharArray()){
            if(Character.isLetter(c)){
                log.debug("Letter {} found as grid index.", c);
                return false;
            }
        }
        int[] bounds = Arrays.stream(gridBounds.split(" "))
                .mapToInt(Integer::parseInt).toArray();
        return bounds.length == GRID_DIMENSIONS && (bounds[0] > 0 || bounds[1] > 0);
    }

}
