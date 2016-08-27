package com.careykevin.batchedtaskexecutor;

import java.util.List;

/**
 * Executes Tasks using the provided BatchedTaskExecutor
 *
 * @author careykevin
 */
public class BatchedTaskExecutorThread extends Thread {

    private final BatchedTaskExecutor executor;

    private final List<Task> tasks;

    public BatchedTaskExecutorThread(BatchedTaskExecutor executor, List<Task> tasks) {
        this.executor = executor;
        this.tasks = tasks;
    }

    @Override
    public void run() {
        executor.execute(tasks);
    }
}
