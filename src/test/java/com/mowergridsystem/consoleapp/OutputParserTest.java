package com.mowergridsystem.consoleapp;

import com.mowergridsystem.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class OutputParserTest {
    private Grid grid;
    private MowerManager mowerManager;
    private List<MowerManager> managers;
    private Position position;
    private Mower mower;
    private static final int GRID_ROW_SIZE = 5;
    private static final int ROW_POSITION = 3;
    private static final int COLUMN_POSITION = 2;


    @BeforeEach
    public void setUp(){
        grid = mock(Grid.class);
        mowerManager = mock(MowerManager.class);
        managers = List.of(mowerManager);
        mower = mock(Mower.class);
        position = mock(Position.class);
    }

    @Test
    public void testComputeOutputString(){
        doReturn(grid).when(mowerManager).getGrid();
        doReturn(GRID_ROW_SIZE).when(grid).getRowsNumber();
        doReturn(mower).when(mowerManager).getMower();
        doReturn(position).when(mower).getPosition();
        doReturn(ROW_POSITION).when(position).getRowCoordinate();
        doReturn(COLUMN_POSITION).when(position).getColumnCoordinate();
        doReturn(OrientationEnum.N).when(mower).getCurrentOrientation();
        Assertions.assertEquals("2 1 N", new OutputParser().computeOutputString(managers));
    }
}
