package com.mowergridsystem.consoleapp;

import com.mowergridsystem.exceptions.BadInputFormatException;
import com.mowergridsystem.exceptions.EmptyInputException;
import com.mowergridsystem.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class InputParser {

    private int gridRowSize;
    private int gridColumnSize;

    public List<MowerManager> parseInputFromFile(File input)
            throws FileNotFoundException, EmptyInputException, BadInputFormatException {
            List<MowerManager> mowerManagers = new ArrayList<>();
            try {
                Scanner scanInput = new Scanner(input);
                String gridBounds = scanInput.hasNextLine() ? scanInput.nextLine() : "";
                Grid grid = createGridFromInputLine(gridBounds);
                while(scanInput.hasNextLine()){
                    String mowerCoordinates = scanInput.nextLine().trim();
                    if(scanInput.hasNextLine()){
                        String mowerCommands = scanInput.nextLine().trim();
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
        grid.invertPositionIsOccupiedState(mower.getPosition());
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
        String rowIndex = positionEntries[1];
        String columnIndex = positionEntries[0];
        Position position = createPosition(rowIndex, columnIndex);
        OrientationEnum orientation = OrientationEnum.valueOf(positionEntries[2]);
        return new StandardMower(position, orientation);
    }

    private Position createPosition(String rowIndex, String columnIndex) {
        int rowIndexValue = (gridRowSize - 1) - Integer.parseInt(rowIndex);
        int columnIndexValue = Integer.parseInt(columnIndex);
        return new Position(rowIndexValue, columnIndexValue);
    }

    private Grid createGridFromInputLine(String gridBounds)
            throws EmptyInputException, BadInputFormatException {
        if(gridBounds.length() == 0)
            throw new EmptyInputException();
        int[] bounds = Arrays.stream(gridBounds.split(" "))
                .mapToInt(Integer::parseInt).toArray();
        if(bounds.length != 2)
            throw new BadInputFormatException();
        gridRowSize = bounds[1] + 1;
        gridColumnSize = bounds[0] + 1;
        return new Grid(gridRowSize, gridColumnSize);
    }
}
