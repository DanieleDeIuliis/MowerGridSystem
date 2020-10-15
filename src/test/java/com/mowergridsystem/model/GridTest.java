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
    public void testInvertPositionIsOccupiedStateFromFalseToTrue(){
        Position position = new Position(1,1);
        grid.invertPositionIsOccupiedState(position);
        Cell cell = grid.getBoard()[position.getRowCoordinate()][position.getColumnCoordinate()];
        Assertions.assertTrue(cell.isOccupied());
        grid.invertPositionIsOccupiedState(position);

    }

    @Test
    public void testGetBoardSuccessfulComparison(){
        Cell[][] clonedBoard = grid.getBoard();
        Position position = new Position(1,1);
        Cell copiedCell = clonedBoard[1][1];
        copiedCell.setOccupied(true);
        Cell originalCell = grid.getBoard()[position.getRowCoordinate()][position.getColumnCoordinate()];
        boolean originalValue = originalCell.isOccupied();
        Assertions.assertNotEquals(originalValue, copiedCell.isOccupied());

    }

}
