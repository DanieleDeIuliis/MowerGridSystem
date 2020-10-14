package com.mowergridsystem.consoleapp;

import com.mowergridsystem.exceptions.BadInputFormatException;
import com.mowergridsystem.exceptions.EmptyInputException;
import com.mowergridsystem.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Queue;

public class InputParserTest {

    @Test
    public void testParseInputWithProperInput()
            throws FileNotFoundException, BadInputFormatException, EmptyInputException {
        File inputTestFile = new File(
                InputParserTest.class.getClassLoader().
                        getResource("inputParser/CorrectInputFormat").getFile());
        List<MowerManager> managers = new InputParser().parseInputFromFile(inputTestFile);
        Grid grid = managers.get(0).getGrid();
        Assertions.assertEquals(5, grid.getRowsNumber());
        Assertions.assertEquals(5, grid.getColumnsNumber());
        Mower mower = managers.get(0).getMower();
        Assertions.assertEquals(3,mower.getPosition().getRowCoordinate());
        Assertions.assertEquals(2,mower.getPosition().getColumnCoordinate());
        Assertions.assertEquals(OrientationEnum.N, mower.getCurrentOrientation());
        Queue<CommandEnum> commands = managers.get(0).getCommands();
        Assertions.assertEquals(3,commands.size());
        Assertions.assertEquals(CommandEnum.L, commands.poll());
        Assertions.assertEquals(CommandEnum.R, commands.poll());
        Assertions.assertEquals(CommandEnum.F, commands.poll());
    }

    @Test
    public void testParseInputWithProperMultipleMowers()
            throws FileNotFoundException, BadInputFormatException, EmptyInputException {
        File inputTestFile = new File(
                InputParserTest.class.getClassLoader().
                        getResource("inputParser/CorrectInputMultiMowers").getFile());
        List<MowerManager> managers = new InputParser().parseInputFromFile(inputTestFile);
        Assertions.assertEquals(2, managers.size());
    }

    @Test
    public void testParseInputFileNotFound(){
        Assertions.assertThrows(FileNotFoundException.class, () -> {
            new InputParser().parseInputFromFile(new File(""));
        });
    }

    @Test
    public void testParseInputEmptyFile(){
        Assertions.assertThrows(EmptyInputException.class, () -> {
            File inputTestFile = new File(
                    InputParserTest.class.getClassLoader().
                            getResource("inputParser/EmptyInput").getFile());
            new InputParser().parseInputFromFile(inputTestFile);
        });
    }

    @Test
    public void testParseInputWrongGridFormat(){
        Assertions.assertThrows(BadInputFormatException.class, () -> {
            File inputTestFile = new File(
                    InputParserTest.class.getClassLoader().
                            getResource("inputParser/WrongGridFormat").getFile());
            new InputParser().parseInputFromFile(inputTestFile);
        });
    }

    @Test
    public void testParseInputWrongMowerFormat(){
        Assertions.assertThrows(BadInputFormatException.class, () -> {
            File inputTestFile = new File(
                    InputParserTest.class.getClassLoader().
                            getResource("inputParser/WrongMowerFormat").getFile());
            new InputParser().parseInputFromFile(inputTestFile);
        });
    }

    @Test
    public void testParseInputMowerHasNoCommands(){
        Assertions.assertThrows(BadInputFormatException.class, () -> {
            File inputTestFile = new File(
                    InputParserTest.class.getClassLoader().
                            getResource("inputParser/WrongMowerFormatV2").getFile());
            new InputParser().parseInputFromFile(inputTestFile);
        });
    }
}
