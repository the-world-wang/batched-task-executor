package com.flake.batchedtaskexecutor;

/**
 * A factory for creating new BatchedTaskExecutor instances
 * 
 * @author careykevin
 *
 */
public interface BatchedTaskExecutorFactory {

	public BatchedTaskExecutor newExecutor();
}
