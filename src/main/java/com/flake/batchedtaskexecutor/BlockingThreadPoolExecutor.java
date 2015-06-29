package com.flake.batchedtaskexecutor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A ThreadPoolExecutor that blocks until a thread is available
 * 
 * @author careykevin
 *
 */
public class BlockingThreadPoolExecutor extends ThreadPoolExecutor {

	private final Logger logger = LoggerFactory.getLogger(BatchedTaskExecutorService.class);

	private final Semaphore semaphore;

	public BlockingThreadPoolExecutor(final int poolSize, final int queueSize) {
		super(poolSize, poolSize, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		semaphore = new Semaphore(poolSize + queueSize);
	}

	@Override
	public void execute(final Runnable task) {
		boolean acquired = false;
		do {
			try {
				semaphore.acquire();
				acquired = true;
			} catch (final InterruptedException ex) {
				logger.error("BlockingExecutor exception", ex);
			}
		} while (!acquired);

		try {
			super.execute(task);
		} catch (final RejectedExecutionException ex) {
			semaphore.release();
			throw ex;
		}
	}

	@Override
	protected void afterExecute(final Runnable r, final Throwable t) {
		super.afterExecute(r, t);
		semaphore.release();
	}
}
