package com.mowergridsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Position {
    private int columnCoordinate;
    private int rowCoordinate;
}
