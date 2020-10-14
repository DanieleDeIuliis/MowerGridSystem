package com.mowergridsystem.consoleapp;

import com.mowergridsystem.exceptions.BadInputFormatException;
import com.mowergridsystem.exceptions.EmptyInputException;
import com.mowergridsystem.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class InputParser {

    private int gridRowSize;

    public List<MowerManager> parseInputFromFile(File input)
            throws FileNotFoundException, EmptyInputException, BadInputFormatException {
            List<MowerManager> mowerManagers = new ArrayList<>();
            try {
                Scanner scanInput = new Scanner(input);
                String gridBounds = scanInput.hasNextLine() ? scanInput.nextLine() : "";
                Grid grid = createGridFromInputLine(gridBounds);
                while(scanInput.hasNextLine()){
                    String mowerCoordinates = scanInput.nextLine();
                    if(scanInput.hasNextLine()){
                        String mowerCommands = scanInput.nextLine();
                        mowerManagers.add(createMowerManagerFromInput(mowerCoordinates,mowerCommands, grid));
                    }else
                        throw new BadInputFormatException();

                }
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException("Input file not found. Insert an existing file name");
            }
            return mowerManagers;
    }

    private MowerManager createMowerManagerFromInput(String mowerCoordinates, String mowerCommands,
                                                     Grid grid)
            throws BadInputFormatException {
        Mower mower = createMowerFromInput(mowerCoordinates);
        Queue<CommandEnum> commands = createCommandsQueue(mowerCommands);
        return new MowerManager(mower, commands, grid);

    }

    private Queue<CommandEnum> createCommandsQueue(String mowerCommands) {
        Queue<CommandEnum> commands = new LinkedList<>();
        for(char c : mowerCommands.toCharArray()){
            commands.add(CommandEnum.valueOf(String.valueOf(c)));
        }
        return commands;
    }

    private Mower createMowerFromInput(String mowerCoordinates)
            throws BadInputFormatException {
        String[] positionEntries = mowerCoordinates.split(" ");
        if(positionEntries.length != 3)
            throw new BadInputFormatException();
        Position position = createPosition(positionEntries[0], positionEntries[1]);
        OrientationEnum orientation = OrientationEnum.valueOf(positionEntries[2]);
        return new StandardMower(position, orientation);
    }

    private Position createPosition(String rowIndex, String columnIndex) {
        int rowIndexValue = convertRowValue(rowIndex);
        int columnIndexValue = Integer.parseInt(columnIndex);
        return new Position(rowIndexValue, columnIndexValue);
    }

    /**
     *
     * @param rowIndex string value of the row coordinate starting from the bottom of the grid
     * @return the int value of the row coordinate starting from the top to follow the usual notation for matrix
     */
    private int convertRowValue(String rowIndex) {
        int rowIntValue = Integer.parseInt(rowIndex);
        return (gridRowSize - 1) - rowIntValue;
    }

    private Grid createGridFromInputLine(String gridBounds)
            throws EmptyInputException, BadInputFormatException {
        if(gridBounds.length() == 0)
            throw new EmptyInputException();
        int[] bounds = Arrays.stream(gridBounds.split(" "))
                .mapToInt(Integer::parseInt).toArray();
        if(bounds.length != 2)
            throw new BadInputFormatException();
        gridRowSize = bounds[0];
        return new Grid(bounds[0], bounds[1]);
    }
}
