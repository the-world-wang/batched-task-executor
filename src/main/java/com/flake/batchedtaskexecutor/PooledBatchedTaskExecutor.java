package com.flake.batchedtaskexecutor;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PooledBatchedTaskExecutor implements BatchedTaskExecutor {

	private BatchedTaskExecutorFactory factory;
	
	private BlockingThreadPoolExecutor executor;
	
	private long shutdownTimeout;
	
	private TimeUnit shutdownTimeUnit;
	
	public PooledBatchedTaskExecutor(BatchedTaskExecutorFactory factory, int poolSize, int queueSize, long shutdownTimeout, TimeUnit shutdownTimeUnit) {
		this.factory = factory;
		executor = new BlockingThreadPoolExecutor(poolSize, queueSize);
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
