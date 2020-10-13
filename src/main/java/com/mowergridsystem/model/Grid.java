package com.mowergridsystem.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Arrays;
import java.util.function.Function;

@EqualsAndHashCode
@ToString
public class Grid {

    private Cell[][] board;

    public Grid(int rows, int columns){
        board = new Cell[rows][columns];
        initializeBoard();
    }

    public Cell[][] getBoard(){
        return Arrays.stream(board).map(copyRow).toArray(Cell[][]::new);
    }


    public boolean isCellAtPositionOccupied(Position position){
        return (board[position.getXCoordinate()][position.getYCoordinate()]).isOccupied();
    }

    public void invertPositionIsOccupiedState(Position position){
        Cell currentCell = board[position.getXCoordinate()][position.getYCoordinate()];
        boolean invertedCurrentCellState = !currentCell.isOccupied();
        currentCell.setOccupied(invertedCurrentCellState);
    }

    private void initializeBoard(){
        for(int r = 0; r < board.length; r++){
            for(int c = 0; c < board[0].length; c++){
                board[r][c] = new Cell();
            }
        }
    }

    private Function<Cell[],Cell[]> copyRow = row -> {
        Cell[] copy = new Cell[row.length];
        for(int i = 0; i < row.length; i++){
            copy[i] = new Cell();
            copy[i].setOccupied(row[i].isOccupied());
        }
        return copy;
    };

}
