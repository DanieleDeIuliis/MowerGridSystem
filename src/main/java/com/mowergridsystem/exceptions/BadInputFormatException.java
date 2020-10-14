package com.mowergridsystem.exceptions;

public class BadInputFormatException extends Exception {

    public static final String DEFAULT_MSG =
            "The input has not the correct format. Please check your input file.";
    public BadInputFormatException() {
        super(DEFAULT_MSG);
    }
}
