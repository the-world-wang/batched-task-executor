package com.careykevin.batchedtaskexecutor;

/**
 * A factory for creating new BatchedTaskExecutor instances
 *
 * @author careykevin
 */
public interface BatchedTaskExecutorFactory {

    BatchedTaskExecutor newExecutor();
}
