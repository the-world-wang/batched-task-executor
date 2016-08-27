package com.careykevin.batchedtaskexecutor;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PooledBatchedTaskExecutor implements BatchedTaskExecutor {

    private final BatchedTaskExecutorFactory factory;

    private final BlockingThreadPoolExecutor executor;

    private final long shutdownTimeout;

    private final TimeUnit shutdownTimeUnit;

    public PooledBatchedTaskExecutor(BatchedTaskExecutorFactory factory, int poolSize, int queueSize, long shutdownTimeout, TimeUnit shutdownTimeUnit) {
        this.factory = factory;
        this.executor = new BlockingThreadPoolExecutor(poolSize, queueSize);
        this.shutdownTimeout = shutdownTimeout;
        this.shutdownTimeUnit = shutdownTimeUnit;
    }

    public PooledBatchedTaskExecutor(BatchedTaskExecutorFactory factory, int poolSize, int queueSize) {
        this(factory, poolSize, queueSize, Long.MAX_VALUE, TimeUnit.DAYS);
    }

    @Override
    public void execute(List<Task> tasks) {
        executor.execute(new BatchedTaskExecutorThread(factory.newExecutor(), tasks));
    }

    @Override
    public void shutdown() throws InterruptedException {
        executor.shutdown();
        executor.awaitTermination(shutdownTimeout, shutdownTimeUnit);
    }
}
