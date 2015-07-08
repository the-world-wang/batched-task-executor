# batched-task-executor

## Overview

A Java library used to buffer, and then execute, groups of tasks - a useful pattern when interacting with systems that are more efficient when accessed in bulk.

The BatchedTaskExecutorService may be configured to execute a group of tasks after a maximum number of tasks have been queued, after an amount of time has passed, or both. This ensures that tasks are executed in a timely fashion, while still being grouped in the most efficient way for the application.

## Use cases

### Elasticsearch

Use batched-task-executor with the [Elasticsearch Bulk API](https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-bulk.html) to buffer index operations and execute them efficiently.

### MySQL

Use batched-task-executor to [combine INSERT statements](http://dev.mysql.com/doc/refman/5.0/en/insert-speed.html) into a single command - reducing connection, sending, and parsing time. 

## Getting started

To use batched-task-executor, simply implement your own logic using the BatchedTaskExecutor interface. The example below uses a LoggingBatchedTaskExecutor to log tasks to the console.

```java
// For this example, store pending tasks in memory. You may implement your own 
// buffer (backed with MySQL or Redis, for example) using the TaskBuffer interface.
TaskBufferFactory bufferFactory = new ArrayListTaskBufferFactory();

// For this example, execute tasks by logging them. You may implement your own 
// execution logic using the BatchedTaskExecutor interface.
BatchedTaskExecutor executor = new LoggingBatchedTaskExecutor();

// Buffer five tasks with max delay of two seconds
int maxBufferedTasks = 5;
int maxPendingTimeSeconds = 2;
BatchedTaskExecutorServiceConfig config = 
	new BatchedTaskExecutorServiceConfig(maxBufferedTasks, maxPendingTimeSeconds);

// Create the service
BatchedTaskExecutorService service = 
	new BatchedTaskExecutorService(bufferFactory, executor, config);

// Run some tasks, these will be executed in groups of five
for (int i = 0; i < 10; i++) {
	service.schedule(new Task(UUID.randomUUID().toString()));
}

// Run a couple more
service.schedule(new Task(UUID.randomUUID().toString()));
service.schedule(new Task(UUID.randomUUID().toString()));

// Now wait...the two tasks will be executed after the pending time is reached
Thread.sleep(3000);

// Always shutdown the service to allow tasks to finish
service.shutdown();

```

#### Output

```

maxBufferSize reached: 5
Executing 5 tasks
Received 5 tasks
	ab19c49b-0af5-4740-9e67-46a4873ec814
	adb194d6-728d-4702-98da-535687cecdca
	c61cae3f-b0b5-4319-ab6f-408ff83e2359
	0e56cc82-5b98-4833-8822-c52722def3ad
	6ce0c792-47cc-4408-b902-188c067b1c2b
maxBufferSize reached: 5
Executing 5 tasks
Received 5 tasks
	e23b28e7-ab45-415e-a5f2-e6254e109d51
	180bfef0-0ef3-47dc-91a6-43d3715c68b7
	f73c0458-6db0-4abd-b63b-1b3d8a87d3f9
	2cad7aad-6871-4c13-b0fa-e18ff8870907
	66135065-508f-486b-9d2f-2ef58a3a3dbb
maxPendingTaskTimeSeconds reached: 2
Executing 2 tasks
Received 2 tasks
	bf160472-c95d-4a08-8abd-82bdb378bcc7
	64800f43-705f-450d-8a0e-93bda57c44ac
Shutting down

```

## Multithreading

To run groups of tasks concurrently, simply wrap your BatchedTaskExecutor in the PooledBatchedTaskExecutor.

```
...
int poolSize = 10;
PooledBatchedTaskExecutor pooledExecutor = 
	new PooledBatchedTaskExecutor(myBatchedTaskExecutorFactory, poolSize, poolSize);
BatchedTaskExecutorService service = 
	new BatchedTaskExecutorService(bufferFactory, pooledExecutor, config);
```

## Build

### Build as jar

```
mvn package
```

### Build as jar with dependencies

```
mvn clean compile assembly:single
```
