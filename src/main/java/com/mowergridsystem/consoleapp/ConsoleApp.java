package com.mowergridsystem.consoleapp;

import com.mowergridsystem.exceptions.BadInputFormatException;
import com.mowergridsystem.model.MowerManager;
import com.mowergridsystem.service.MowerMoveService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class ConsoleApp {

    public static String computeMowerGridSystemService(String filePath)
            throws FileNotFoundException, BadInputFormatException, InterruptedException {
        MowerMoveService mowerService = new MowerMoveService();
        List<MowerManager> managers;
        File inputFile = new File(filePath.trim());
        managers = new InputParser().parseInputFromFile(inputFile);
        mowerService.executeCommands(managers);
        return new OutputParser().computeOutputString(managers);
    }

    public static void main(String[] args)
            throws FileNotFoundException, BadInputFormatException, InterruptedException {
        System.out.println("Please insert the full path of the input file including the extension:");
        Scanner inputReader = new Scanner(System.in);
        String filePath = inputReader.nextLine();
        System.out.println(computeMowerGridSystemService(filePath));
    }
}
