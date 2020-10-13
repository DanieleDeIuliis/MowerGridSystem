package com.mowergridsystem.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Queue;

import static org.mockito.Mockito.*;


public class MowerManagerTest {

    private Grid grid = mock(Grid.class);
    private Mower mower = mock(StandardMower.class);
    private Queue<CommandEnum> commands = mock(Queue.class);
    private MowerManager mowerManager = new MowerManager(mower, commands, grid);

    @BeforeEach
    void beforeEach(){
        grid = mock(Grid.class);
        mower = mock(StandardMower.class);
        commands = mock(Queue.class);
        mowerManager = new MowerManager(mower, commands, grid);
    }

    @Test
    public void testExecuteNextCommandTurnLeft(){
        doReturn(CommandEnum.LEFT).when(commands).poll();
        doNothing().when(mower).changeOrientation(any(CommandEnum.class));
        Assertions.assertTrue(mowerManager.executeNextCommand());
    }

    @Test
    public void testExecuteNextCommandMoveFailure(){
        doReturn(CommandEnum.FORWARD).when(commands).poll();
        doReturn(OrientationEnum.NORTH).when(mower).getCurrentOrientation();
        doReturn(new Position(0,0)).when(mower).getPosition();
        doReturn(2).when(grid).getRowsNumber();
        doReturn(2).when(grid).getColumnsNumber();
        Assertions.assertFalse(mowerManager.executeNextCommand());
    }


}
