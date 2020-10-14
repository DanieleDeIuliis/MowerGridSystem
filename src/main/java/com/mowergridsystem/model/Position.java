package com.mowergridsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Position {
    private int rowCoordinate;
    private int columnCoordinate;
}
