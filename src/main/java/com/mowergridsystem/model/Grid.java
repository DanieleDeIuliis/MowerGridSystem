package com.mowergridsystem.model;

import java.util.Arrays;
import java.util.function.Function;


public class Grid {

    private Cell[][] board;

    private final int rows, columns;

    public Grid(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        board = new Cell[rows][columns];
        initializeBoard();
    }

    public Cell[][] getBoard(){
        return Arrays.stream(board).map(copyRow).toArray(Cell[][]::new);
    }


    public boolean isCellOccupied(Position position){
        return (board[position.getRowCoordinate()][position.getColumnCoordinate()]).isOccupied();
    }

    public void invertPositionIsOccupiedState(Position position){
        Cell currentCell = board[position.getRowCoordinate()][position.getColumnCoordinate()];
        boolean invertedCurrentCellState = !currentCell.isOccupied();
        currentCell.setOccupied(invertedCurrentCellState);
    }

    public int getRowsNumber() {
        return rows;
    }

    public int getColumnsNumber() {
        return columns;
    }

    public synchronized boolean checkPositionAndChangeState(Position oldPosition, Position newPosition){
        if(isNewPositionValid(newPosition)){
            invertPositionIsOccupiedState(oldPosition);
            invertPositionIsOccupiedState(newPosition);
            return true;
        }
        return false;
    }

    private void initializeBoard(){
        for(int r = 0; r < board.length; r++)
            for(int c = 0; c < board[0].length; c++)
                board[r][c] = new Cell();
    }

    private Function<Cell[],Cell[]> copyRow = row -> {
        Cell[] copy = new Cell[row.length];
        for(int i = 0; i < row.length; i++){
            copy[i] = new Cell();
            copy[i].setOccupied(row[i].isOccupied());
        }
        return copy;
    };

    private boolean isPositionInsideBoard(Position newPosition){
        int newRow = newPosition.getRowCoordinate();
        int newColumn = newPosition.getColumnCoordinate();
        return newRow >= 0 && newRow < rows && newColumn >= 0 && newColumn < columns;
    }
    public boolean isNewPositionValid(Position newPosition){
        return isPositionInsideBoard(newPosition) && !isCellOccupied(newPosition);
    }

}
