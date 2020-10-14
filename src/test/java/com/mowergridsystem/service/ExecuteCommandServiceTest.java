package com.mowergridsystem.service;

import com.mowergridsystem.consoleapp.InputParser;
import com.mowergridsystem.consoleapp.InputParserTest;
import com.mowergridsystem.exceptions.BadInputFormatException;
import com.mowergridsystem.exceptions.EmptyInputException;
import com.mowergridsystem.model.MowerManager;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class ExecuteCommandServiceTest {

//    @Test
//    public void test()
//            throws FileNotFoundException, BadInputFormatException, EmptyInputException, InterruptedException {
//        File inputTestFile = new File(
//                InputParserTest.class.getClassLoader().
//                        getResource("inputParser/MowerCollision2").getFile());
//        List<MowerManager> managers = new InputParser().parseInputFromFile(inputTestFile);
//        ExecuteCommand executeCommand = new ExecuteCommand();
//        executeCommand.executeCommands(managers);
//        for(MowerManager m : managers){
//            int r = 4 - m.getMower().getPosition().getRowCoordinate();
//            int c = m.getMower().getPosition().getColumnCoordinate();
//        }
//    }
}
