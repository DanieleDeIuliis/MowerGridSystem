package com.mowergridsystem.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Queue;

import static org.mockito.Mockito.*;


public class MowerManagerTest {

    private Grid grid;
    private Mower mower;
    private Queue<CommandEnum> commands;
    private MowerManager mowerManager;

    @BeforeEach
    public void setUp(){
        grid = mock(Grid.class);
        mower = mock(StandardMower.class);
        commands = mock(Queue.class);
        mowerManager = new MowerManager(mower, commands, grid);
    }

    @Test
    public void testExecuteNextCommandTurnLeft(){
        doReturn(CommandEnum.L).when(commands).poll();
        doNothing().when(mower).changeOrientation(any(CommandEnum.class));
        Assertions.assertTrue(mowerManager.executeNextCommand());
    }

    @Test
    public void testExecuteNextCommandMoveForward(){
        doReturn(CommandEnum.F).when(commands).poll();
        doReturn(OrientationEnum.W).when(mower).getCurrentOrientation();
        doReturn(new Position(0,1)).when(mower).getPosition();
        doReturn(true).when(grid).checkPositionAndChangeState(any(Position.class),any(Position.class));
        Assertions.assertTrue(mowerManager.executeNextCommand());
    }

    @Test
    public void testExecuteNextCommandMoveFailure(){
        doReturn(CommandEnum.F).when(commands).poll();
        doReturn(OrientationEnum.N).when(mower).getCurrentOrientation();
        doReturn(new Position(0,0)).when(mower).getPosition();
        doReturn(2).when(grid).getRowsNumber();
        doReturn(2).when(grid).getColumnsNumber();
        Assertions.assertFalse(mowerManager.executeNextCommand());
    }
}
