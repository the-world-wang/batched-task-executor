package com.careykevin.batchedtaskexecutor.example;

import com.careykevin.batchedtaskexecutor.*;

import java.util.UUID;

/**
 * An example usage of the BatchTaskExecutorService
 *
 * @author careykevin
 */
public class BatchedTaskExecutorExample {

    public static void main(String[] args) throws Exception {

        // Store pending tasks in memory
        TaskBufferFactory bufferFactory = new ArrayListTaskBufferFactory();

        // Execute tasks by logging them
        BatchedTaskExecutor executor = new LoggingBatchedTaskExecutor();

        // Buffer five tasks with max delay of 2 seconds
        int maxBufferedTasks = 5;
        int maxPendingTimeSeconds = 2;
        BatchedTaskExecutorServiceConfig config = new BatchedTaskExecutorServiceConfig(maxBufferedTasks, maxPendingTimeSeconds);

        // Create the service
        BatchedTaskExecutorService service = new BatchedTaskExecutorService(bufferFactory, executor, config);

        // Run some tasks, these will be executed in groups
        for (int i = 0; i < 10; i++) {
            service.schedule(new Task(UUID.randomUUID().toString()));
        }

        // Run a couple more
        service.schedule(new Task(UUID.randomUUID().toString()));
        service.schedule(new Task(UUID.randomUUID().toString()));

        // But now wait...pending tasks will be executed after pending time is reached
        Thread.sleep(3000);

        // Always shutdown the service to allow tasks to finish
        service.shutdown();
    }
}
