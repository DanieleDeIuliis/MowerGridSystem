package com.mowergridsystem.exceptions;

public class EmptyInputException extends Exception{
    public static final String DEFAULT_MSG =
            "Empty input line. Please check the validity of the input file.";

    public EmptyInputException(){
        super(DEFAULT_MSG);
    }
}
