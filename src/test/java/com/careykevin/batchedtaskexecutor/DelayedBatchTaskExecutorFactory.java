package com.careykevin.batchedtaskexecutor;

public class DelayedBatchTaskExecutorFactory implements BatchedTaskExecutorFactory {

    private final long delayMillis;

    public DelayedBatchTaskExecutorFactory(long delayMillis) {
        this.delayMillis = delayMillis;
    }

    public DelayedBatchTaskExecutorFactory() {
        this(0);
    }

    public BatchedTaskExecutor newExecutor() {
        return new DelayedBatchTaskExecutor(delayMillis);
    }
}
