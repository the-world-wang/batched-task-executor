package com.flake.batchedtaskexecutor;

import java.util.List;

/**
 * A BatchedTaskExecutor with a configurable delay, useful for testing
 * 
 * @author careykevin
 *
 */
public class DelayedBatchTaskExecutor implements BatchedTaskExecutor {

	private long delayMillis;
	
	public static int totalTasksExecuted = 0;
	
	public DelayedBatchTaskExecutor(long delayMillis) {
		this.delayMillis = delayMillis;
	}
	
	public DelayedBatchTaskExecutor() {
		this(0);
	}
	
	@Override
	public void execute(List<Task> tasks) {
		try {
			Thread.sleep(delayMillis);
			totalTasksExecuted += tasks.size();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void shutdown() {
		// no-op
	}
}
