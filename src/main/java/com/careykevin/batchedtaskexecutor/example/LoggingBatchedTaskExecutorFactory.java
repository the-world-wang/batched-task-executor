package com.careykevin.batchedtaskexecutor.example;

import com.careykevin.batchedtaskexecutor.BatchedTaskExecutor;
import com.careykevin.batchedtaskexecutor.BatchedTaskExecutorFactory;

public class LoggingBatchedTaskExecutorFactory implements BatchedTaskExecutorFactory {

    @Override
    public BatchedTaskExecutor newExecutor() {
        return new LoggingBatchedTaskExecutor();
    }
}
