package com.mowergridsystem.service;

import com.mowergridsystem.model.MowerManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ExecuteCommand {

    public void executeCommands(List<MowerManager> mowerManagers) throws InterruptedException {
        int workingManagerSize = mowerManagers.size();
        ExecutorService threadPool = Executors.newFixedThreadPool(workingManagerSize);
        List<MowerManager> workingManagers = new ArrayList<>(mowerManagers);
        while(workingManagerSize > 0){
            CountDownLatch countDownLatch = new CountDownLatch(workingManagerSize);
            for(MowerManager manager : workingManagers){
                threadPool.submit(() -> {
                    manager.executeNextCommand();
                    countDownLatch.countDown();
                });
                TimeUnit.MICROSECONDS.sleep(1);
            }
            countDownLatch.await();
            workingManagers = workingManagers.stream()
                    .filter(m -> !(m.getCommands()).isEmpty())
                    .collect(Collectors.toList());
            workingManagerSize = workingManagers.size();
        }
        threadPool.shutdown();
    }
}
