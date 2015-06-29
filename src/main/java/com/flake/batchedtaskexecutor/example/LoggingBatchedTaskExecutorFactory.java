package com.flake.batchedtaskexecutor.example;

import com.flake.batchedtaskexecutor.BatchedTaskExecutor;
import com.flake.batchedtaskexecutor.BatchedTaskExecutorFactory;

public class LoggingBatchedTaskExecutorFactory implements BatchedTaskExecutorFactory {

	@Override
	public BatchedTaskExecutor newExecutor() {
		return new LoggingBatchedTaskExecutor();
	}
}
