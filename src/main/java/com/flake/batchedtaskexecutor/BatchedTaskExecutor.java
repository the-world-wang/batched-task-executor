package com.flake.batchedtaskexecutor;

import java.util.List;

/**
 * Executes a list of Tasks
 * 
 * @author careykevin
 *
 */
public interface BatchedTaskExecutor {

	/**
	 * Execute the given list of Tasks
	 * 
	 * @param tasks
	 */
	void execute(List<Task> tasks);
	
	/**
	 * Shutdown the executor, wait for execution to finish
	 * 
	 * @throws InterruptedException
	 */
	void shutdown() throws InterruptedException;
}
