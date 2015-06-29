package com.flake.batchedtaskexecutor.example;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flake.batchedtaskexecutor.BatchedTaskExecutor;
import com.flake.batchedtaskexecutor.Task;

/**
 * A BatchedTaskExecutor that logs Tasks data, useful for examples
 * 
 * @author careykevin
 *
 */
public class LoggingBatchedTaskExecutor implements BatchedTaskExecutor {

	private final Logger logger = LoggerFactory.getLogger(LoggingBatchedTaskExecutor.class);

	@Override
	public void execute(List<Task> tasks) {
		logger.info("Received {} tasks", tasks.size());
		for (Task task : tasks) {
			logger.info("\t" + task.getId());
		}
	}
	
	@Override
	public void shutdown() {
		// no-op
	}
}
