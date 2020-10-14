package com.mowergridsystem.consoleapp;

import com.mowergridsystem.exceptions.BadInputFormatException;
import com.mowergridsystem.exceptions.EmptyInputException;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class ConsoleAppTest {
    private static final String INPUT_DIR = "consoleApp/input/";
    private static final String OUTPUT_DIR = "consoleApp/output/";
    private static final String FILE_EXTENSION = ".txt";


    @ParameterizedTest
    @ValueSource(strings = {"singleMower", "multipleMowers", "mowerCollision", "moveAlwaysLeft", "mowerCrash"})
    public void testCallComputePriceAndTaxesService(String fileName)
            throws FileNotFoundException, InterruptedException, BadInputFormatException, EmptyInputException {
        String pathToTestFile =
                this.getClass().getClassLoader().
                        getResource(INPUT_DIR + fileName + FILE_EXTENSION).getFile();
        String result = ConsoleApp.computeMowerGridSystemService(pathToTestFile);
        String expectedOutput = getExpectedResult(fileName);
        Assertions.assertEquals(expectedOutput, result);
    }

    private String getExpectedResult(String fileName) throws FileNotFoundException {
        StringBuilder outputBuilder = new StringBuilder();
        File outputFile = new File(this.getClass().getClassLoader().
                getResource(OUTPUT_DIR + fileName + FILE_EXTENSION).getFile());
        Scanner outputScan = new Scanner(outputFile);
        while(outputScan.hasNextLine()){
            outputBuilder.append(outputScan.nextLine()).append("\n");
        }
        return outputBuilder.toString().trim();
    }
}
