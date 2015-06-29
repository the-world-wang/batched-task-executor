package com.flake.batchedtaskexecutor;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

public class BatchedTaskExecutorServiceTest {
	
	@Test
	public void testMaxBufferSize() {
		BatchedTaskExecutorService service = newService(new BatchedTaskExecutorServiceConfig(2, 0));
		service.schedule(newTask());
		assertEquals(service.getTotalTasksExecuted(), 0);
		service.schedule(newTask());
		assertEquals(service.getTotalTasksExecuted(), 2);
		service.schedule(newTask());
		assertEquals(service.getTotalTasksExecuted(), 2);
	}
	
	@Test
	public void testExecuteOnShutdown() throws InterruptedException {
		BatchedTaskExecutorService service = newService(new BatchedTaskExecutorServiceConfig(2, 0));
		service.schedule(newTask());
		service.shutdown();
		assertEquals(service.getTotalTasksExecuted(), 1);
	}
	
	@Test
	public void testMaxPendingTime() throws InterruptedException {
		BatchedTaskExecutorService service = newService(new BatchedTaskExecutorServiceConfig(2, 1));
		service.schedule(newTask());
		Thread.sleep(1500);
		assertEquals(service.getTotalTasksExecuted(), 1);
	}
	
	private BatchedTaskExecutorService newService(BatchedTaskExecutorServiceConfig config) {
		return new BatchedTaskExecutorService(new ArrayListTaskBufferFactory(), new DelayedBatchTaskExecutor(), config);
	}
	
	private Task newTask() {
		return new Task(UUID.randomUUID().toString(), null, null);
	}
}
