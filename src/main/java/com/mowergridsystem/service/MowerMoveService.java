package com.mowergridsystem.service;

import com.mowergridsystem.model.MowerManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MowerMoveService {

    /**
     * Computes all the commands queue of the managers executing one step at a time.
     * A step represents the execution of the next command for each active manager.
     * It assumes that changing direction and moving require that same amount of time.
     * @param mowerManagers the managers that have mowers to move
     * @throws InterruptedException
     */
    public void executeCommands(List<MowerManager> mowerManagers) throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(mowerManagers.size());
        List<MowerManager> workingManagers = new ArrayList<>(mowerManagers);
        while(workingManagers.size() > 0){
            executeStep(workingManagers, threadPool);
            workingManagers = workingManagers.stream()
                    .filter(m -> !(m.getCommands()).isEmpty())
                    .collect(Collectors.toList());
        }
        threadPool.shutdown();
    }

    /**
     * Computes one global step: Executes the next command of all the working manager.
     * After submitting the job to a thread in the pool.
     *
     * It waits 1 MICROSECOND to ensure that the behaviour during the tests is deterministic.
     *
     * @param workingManagers the managers that have still commands to execute
     * @param threadPool the thread pool that will execute the commands. One per manager
     * @throws InterruptedException
     */
    private void executeStep(List<MowerManager> workingManagers, ExecutorService threadPool)
            throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(workingManagers.size());
        for(MowerManager manager : workingManagers){
            threadPool.submit(() -> {
                manager.executeNextCommand();
                countDownLatch.countDown();
            });
            TimeUnit.MICROSECONDS.sleep(1);
        }
        countDownLatch.await();

    }
}
