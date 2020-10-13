package com.mowergridsystem.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GridTest {
    public static Grid grid;

    @BeforeAll
    public static void init(){
        grid = new Grid(5,5);
    }

    @Test
    public void invertPositionIsOccupiedStateTestFromFalseToTrue(){
        Position position = new Position(1,1);
        grid.invertPositionIsOccupiedState(position);
        Assertions.assertTrue(grid.isCellOccupied(position));
        grid.invertPositionIsOccupiedState(position);

    }

    @Test
    public void getBoardTestSuccessfulComparison(){
        Cell[][] clonedBoard = grid.getBoard();
        Position position = new Position(1,1);
        Cell copiedCell = clonedBoard[1][1];
        copiedCell.setOccupied(true);
        boolean originalValue = grid.isCellOccupied(position);
        Assertions.assertNotEquals(originalValue, copiedCell.isOccupied());

    }

}
