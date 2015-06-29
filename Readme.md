# batched-task-executor

## Overview

A Java library used to buffer, and then execute, groups of tasks - a useful pattern when interacting with systems that are more efficient when accessed in bulk.

## Example uses

Use the batched-task-executor with Elasticsearch to buffer index operations and execute through the [Bulk API](https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-bulk.html)

## Getting started

To use batched-task-executor, simply implement your own logic with the BatchedTaskExecutor interface. The example below uses a LoggingBatchedTaskExecutor to log Tasks to the console.

```
// Store pending tasks in memory
TaskBufferFactory bufferFactory = new ArrayListTaskBufferFactory();

// Execute tasks by logging them
BatchedTaskExecutor executor = new LoggingBatchedTaskExecutor();

// Buffer five tasks with max delay of 2 seconds
int maxBufferedTasks = 5;
int maxPendingTimeSeconds = 2;
BatchedTaskExecutorServiceConfig config = new BatchedTaskExecutorServiceConfig(maxBufferedTasks, maxPendingTimeSeconds);

// Create the service
BatchedTaskExecutorService service = new BatchedTaskExecutorService(bufferFactory, executor, config);

// Run some tasks, these will be executed in groups
for (int i = 0; i < 10; i++) {
	service.schedule(new Task(UUID.randomUUID().toString()));
}

// Run a couple more
service.schedule(new Task(UUID.randomUUID().toString()));
service.schedule(new Task(UUID.randomUUID().toString()));

// But now wait...pending tasks will be executed after pending time is reached
Thread.sleep(3000);

// Always shutdown the service to allow tasks to finish
service.shutdown();
```

## Multithreading

Use the PooledBatchTaskExecutor to execute groups of tasks concurrently.

