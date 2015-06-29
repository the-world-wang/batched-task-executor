package com.flake.batchedtaskexecutor;

import org.junit.Test;

public class BatchedTaskExecutorServiceConfigTest {

	@Test(expected = IllegalArgumentException.class)
	public void testMaxBufferSizeZero() {
		BatchedTaskExecutorServiceConfig config = new BatchedTaskExecutorServiceConfig(0, 0);
		new BatchedTaskExecutorService(new ArrayListTaskBufferFactory(), new DelayedBatchTaskExecutor(), config);
	}
}
