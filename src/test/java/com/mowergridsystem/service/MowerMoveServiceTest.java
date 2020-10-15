package com.mowergridsystem.service;


import com.mowergridsystem.model.CommandEnum;
import com.mowergridsystem.model.MowerManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Queue;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class MowerMoveServiceTest {

    private List<MowerManager> managers;
    private MowerManager mowerManager;
    private Queue<CommandEnum> commands;

    @BeforeEach
    public void setUp(){
        mowerManager = mock(MowerManager.class);
        managers = List.of(mowerManager, mowerManager, mowerManager);
        commands = mock(Queue.class);
    }

    @Test
    public void testExecuteCommands() {
        doReturn(true).when(mowerManager).executeNextCommand();
        doReturn(commands).when(mowerManager).getCommands();
        doReturn(true).when(commands).isEmpty();
        MowerMoveService mowerMoveService = new MowerMoveService();
        Assertions.assertDoesNotThrow(() -> mowerMoveService.executeCommands(managers));
    }
}
