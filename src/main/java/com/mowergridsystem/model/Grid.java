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

    /**
     * @return a deep copy of the board
     */
    public Cell[][] getBoard(){
        return Arrays.stream(board).map(copyRow).toArray(Cell[][]::new);
    }

    /**
     * Checks whether the new position is inside the board and if the cell is already occupied.
     * In addition, if the new position is valid, changes the state of the two cells. It frees
     * first one, and occupy the second. The synchronized ensures that only one thread at a time
     * is able to request the state change of the board.
     * @param oldPosition Original position
     * @param newPosition Position to move in
     * @return The validity of the new position.
     */
    public synchronized boolean checkPositionAndChangeState(Position oldPosition, Position newPosition){
        if(isNewPositionValid(newPosition)){
            invertPositionIsOccupiedState(oldPosition);
            invertPositionIsOccupiedState(newPosition);
            return true;
        }
        return false;
    }

    /**
     * Inverts the state of a cell which could be either free or occupied
     * @param position position representing the cell to change
     */
    public synchronized void invertPositionIsOccupiedState(Position position){
        Cell currentCell = board[position.getRowCoordinate()][position.getColumnCoordinate()];
        boolean invertedCurrentCellState = !currentCell.isOccupied();
        currentCell.setOccupied(invertedCurrentCellState);
    }

    public synchronized boolean isNewPositionValid(Position newPosition){
        return isPositionInsideBoard(newPosition) && !isCellOccupied(newPosition);
    }

    public int getRowsNumber() {
        return rows;
    }

    public int getColumnsNumber() {
        return columns;
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

    private boolean isCellOccupied(Position position){
        return board[position.getRowCoordinate()][position.getColumnCoordinate()].isOccupied();
    }

}
