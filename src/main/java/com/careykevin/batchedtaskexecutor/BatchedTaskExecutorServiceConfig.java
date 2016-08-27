package com.careykevin.batchedtaskexecutor;

/**
 * Configuration options for a BatchedTaskExecutorService
 *
 * @author careykevin
 */
public class BatchedTaskExecutorServiceConfig {

    private int maxBufferedTasks;

    private int maxPendingTaskTimeSeconds;

    /**
     * @param maxBufferedTasks          The maximum number of Tasks that may be stored in a buffer.
     *                                  When the limit is reached, the tasks will be executed
     * @param maxPendingTaskTimeSeconds The maximum time, in seconds, that Tasks may be stored in a
     *                                  buffer. When the limit is reach, the tasks will be executed
     */
    public BatchedTaskExecutorServiceConfig(int maxBufferedTasks, int maxPendingTaskTimeSeconds) {

        if (maxBufferedTasks <= 0) {
            throw new IllegalArgumentException("maxBufferedTasks must be greater than zero");
        }

        this.maxBufferedTasks = maxBufferedTasks;
        this.maxPendingTaskTimeSeconds = maxPendingTaskTimeSeconds;
    }

    public BatchedTaskExecutorServiceConfig(int maxBufferedTasks) {
        this(maxBufferedTasks, 0);
    }

    public int getMaxBufferedTasks() {
        return maxBufferedTasks;
    }

    public int getMaxPendingTaskTimeSeconds() {
        return maxPendingTaskTimeSeconds;
    }
}
