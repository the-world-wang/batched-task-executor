package com.flake.batchedtaskexecutor;

import java.util.List;

/**
 * A BatchedTaskExecutor with a configurable delay, useful for testing. A no-op
 * when used with a delay of 0.
 * 
 * @author careykevin
 *
 */
public class DelayedBatchTaskExecutor implements BatchedTaskExecutor {

	private long delayMillis;
	
	public static int TOTAL_TASKS_EXECUTED = 0;
	
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
			TOTAL_TASKS_EXECUTED += tasks.size();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void shutdown() {
		// no-op
	}
}
