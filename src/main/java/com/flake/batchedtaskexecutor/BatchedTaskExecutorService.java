package com.flake.batchedtaskexecutor;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to buffer, and then execute, groups of tasks - a useful pattern when interacting with 
 * systems that are more efficient when accessed in bulk.
 *   
 * @author careykevin
 *
 */
public class BatchedTaskExecutorService {

	private final Logger logger = LoggerFactory.getLogger(BatchedTaskExecutorService.class);
	
	private TaskBufferFactory bufferFactory;
	
	private BatchedTaskExecutor executor;
	
	private BatchedTaskExecutorServiceConfig config;
	
	private TaskBuffer buffer;

	private final Object bufferLock = new Object();
	
	private long bufferStartTime;

	private Timer bufferTimer;

	private long totalTasksScheduled;
	
	private long totalTasksExecuted;
	
	private boolean isShutDown;
	
	/**
	 * Instantiate a new service - callers must shutdown() when finish
	 * 
	 * @param bufferFactory A factory to return instances of the TaskBuffer implemented used to buffer Task objects
	 * @param executor The executor that will be used to handle groups of tasks
	 * @param config Service configuration values
	 */
	public BatchedTaskExecutorService(TaskBufferFactory bufferFactory, BatchedTaskExecutor executor, BatchedTaskExecutorServiceConfig config) {
		
		this.bufferFactory = bufferFactory;
		this.executor = executor;
		this.config = config;
		
		int maxPendingTimeSeconds = config.getMaxPendingTaskTimeSeconds();
		if (maxPendingTimeSeconds > 0) {
			bufferTimer = new Timer();
			bufferTimer.schedule(new TaskExecutingTimerTask(), maxPendingTimeSeconds, maxPendingTimeSeconds);
		}
	}
	
	private class TaskExecutingTimerTask extends TimerTask {
		public void run() {
			synchronized (bufferLock) {
				long pendingTimeSeconds = (System.currentTimeMillis() - bufferStartTime) / 1000;
				if ((pendingTimeSeconds >= config.getMaxPendingTaskTimeSeconds()) && (buffer != null)) {
					logger.debug("maxPendingTaskTimeSeconds reached: {}", pendingTimeSeconds);
					executeTasks();
				}
			}
		}
	}
	
	/**
	 * Schedule the execution of a new task
	 * 
	 * @param task
	 */
	public void schedule(Task task) {

		synchronized (bufferLock) {

			if (isShutDown) {
				throw new IllegalStateException("Service has been shut down");
			}

			if (buffer == null) {
				buffer = bufferFactory.newTaskBuffer();
				bufferStartTime = System.currentTimeMillis();
			}

			buffer.add(task);
			
			totalTasksScheduled++;
			
			if (buffer.size() == config.getMaxBufferedTasks()) {
				logger.debug("maxBufferSize reached: {}", buffer.size());
				executeTasks();
			}
		}
	}
	
	private void executeTasks() {
		if (buffer != null) {
			List<Task> tasks = buffer.getTasks();
			logger.debug("Executing {} tasks", tasks.size());
			executor.execute(tasks);
			totalTasksExecuted += tasks.size();
			buffer = null;
			bufferStartTime = 0;
		}
	}
	
	/**
	 * Shut down the service, waits for existing tasks to be completed
	 * 
	 * @throws InterruptedException
	 */
	public void shutdown() throws InterruptedException {
		synchronized (bufferLock) {
			logger.debug("Shutting down");
			if (bufferTimer != null) {
				bufferTimer.cancel();
			}
			isShutDown = true;
			executeTasks();
			executor.shutdown();
		}
	}
	
	public void resetStatus() {
		totalTasksScheduled = 0;
		totalTasksExecuted = 0;
	}
	
	public long getTotalTasksExecuted() {
		return totalTasksExecuted;
	}
	
	public long getTotalTasksScheduled() {
		return totalTasksScheduled;
	}
}
