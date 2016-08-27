package com.careykevin.batchedtaskexecutor;

import java.util.List;

/**
 * Executes a list of Tasks
 *
 * @author careykevin
 */
public interface BatchedTaskExecutor {

    /**
     * Execute the given list of Tasks
     *
     * @param tasks The tasks to execute
     */
    void execute(List<Task> tasks);

    /**
     * Shutdown the executor, wait for execution to finish
     *
     * @throws InterruptedException
     */
    void shutdown() throws InterruptedException;
}
