package com.flake.batchedtaskexecutor;

public class DelayedBatchTaskExecutorFactory implements BatchedTaskExecutorFactory {

	private long delayMillis;
	
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
