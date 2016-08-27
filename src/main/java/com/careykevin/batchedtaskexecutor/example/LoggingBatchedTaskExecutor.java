package com.careykevin.batchedtaskexecutor.example;

import java.util.List;

import com.careykevin.batchedtaskexecutor.BatchedTaskExecutor;
import com.careykevin.batchedtaskexecutor.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A BatchedTaskExecutor that logs Tasks data, useful for examples
 *
 * @author careykevin
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
