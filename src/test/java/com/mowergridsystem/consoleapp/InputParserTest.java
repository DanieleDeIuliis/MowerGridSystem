package com.mowergridsystem.consoleapp;

import com.mowergridsystem.exceptions.BadInputFormatException;
import com.mowergridsystem.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Queue;

public class InputParserTest {

    public static final String TEST_DIR = "inputParser/";
    public static final String MOWER_DIR = "mower/";
    public static final String GRID_DIR = "grid/";

    @Test
    public void testParseInputWithProperInput()
            throws FileNotFoundException, BadInputFormatException {
        File inputTestFile = new File(
                InputParserTest.class.getClassLoader().
                        getResource(TEST_DIR + "correctInputFormat").getFile());
        List<MowerManager> managers = new InputParser().parseInputFromFile(inputTestFile);
        Grid grid = managers.get(0).getGrid();
        Assertions.assertEquals(6, grid.getRowsNumber());
        Assertions.assertEquals(6, grid.getColumnsNumber());
        Mower mower = managers.get(0).getMower();
        Assertions.assertEquals(3,mower.getPosition().getRowCoordinate());
        Assertions.assertEquals(1,mower.getPosition().getColumnCoordinate());
        Assertions.assertEquals(OrientationEnum.N, mower.getCurrentOrientation());
        Queue<CommandEnum> commands = managers.get(0).getCommands();
        Assertions.assertEquals(3,commands.size());
        Assertions.assertEquals(CommandEnum.L, commands.poll());
        Assertions.assertEquals(CommandEnum.R, commands.poll());
        Assertions.assertEquals(CommandEnum.F, commands.poll());
    }

    @Test
    public void testParseInputWithProperMultipleMowers()
            throws FileNotFoundException, BadInputFormatException {
        File inputTestFile = new File(
                InputParserTest.class.getClassLoader().
                        getResource(TEST_DIR + "correctInputMultiMowers").getFile());
        List<MowerManager> managers = new InputParser().parseInputFromFile(inputTestFile);
        Assertions.assertEquals(2, managers.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"noOrientation", "wrongOrientation","negativePositionOutOfBounds", "positionOutOfBounds", "lettersAsIndex","emptyCommands","wrongCommands"})
    public void testParseInputMowerNotCreated(String fileName)
            throws FileNotFoundException, BadInputFormatException {
        File inputTestFile = new File(
                InputParserTest.class.getClassLoader().
                        getResource(TEST_DIR + MOWER_DIR + fileName).getFile());
        List<MowerManager> managers = new InputParser().parseInputFromFile(inputTestFile);
        Assertions.assertEquals(0, managers.size());
    }

    @Test
    public void testOnlyTheFirstMowerIsCreated()
            throws FileNotFoundException, BadInputFormatException {
        File inputTestFile = new File(
                InputParserTest.class.getClassLoader().
                        getResource(TEST_DIR + MOWER_DIR +  "mowersInSamePosition").getFile());
        List<MowerManager> managers = new InputParser().parseInputFromFile(inputTestFile);
        Assertions.assertEquals(1, managers.size());
        Assertions.assertEquals(OrientationEnum.N, managers.get(0).getMower().getCurrentOrientation());
    }

    @Test
    public void testParseInputFileNotFound(){
        Assertions.assertThrows(FileNotFoundException.class, () -> {
            new InputParser().parseInputFromFile(new File(""));
        });
    }

    @Test
    public void testParseInputEmptyFile(){
        Assertions.assertThrows(BadInputFormatException.class, () -> {
            File inputTestFile = new File(
                    InputParserTest.class.getClassLoader().
                            getResource(TEST_DIR + "emptyInput").getFile());
            new InputParser().parseInputFromFile(inputTestFile);
        });
    }

    @Test
    public void testParseInputWrongGridFormat(){
        Assertions.assertThrows(BadInputFormatException.class, () -> {
            File inputTestFile = new File(
                    InputParserTest.class.getClassLoader().
                            getResource(TEST_DIR + GRID_DIR + "wrongFormat").getFile());
            new InputParser().parseInputFromFile(inputTestFile);
        });
    }

    @Test
    public void testParseInputWrongGridValues(){
        Assertions.assertThrows(BadInputFormatException.class, () -> {
            File inputTestFile = new File(
                    InputParserTest.class.getClassLoader().
                            getResource(TEST_DIR + GRID_DIR + "wrongValues").getFile());
            new InputParser().parseInputFromFile(inputTestFile);
        });
    }

    @Test
    public void testParseInputLetterInGridValues(){
        Assertions.assertThrows(BadInputFormatException.class, () -> {
            File inputTestFile = new File(
                    InputParserTest.class.getClassLoader().
                            getResource(TEST_DIR + GRID_DIR + "wrongValuesWithLetters").getFile());
            new InputParser().parseInputFromFile(inputTestFile);
        });
    }
}
