package com.careykevin.batchedtaskexecutor;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(BatchedTaskExecutorServiceMultithreadedTest.class)
public class BatchedTaskExecutorServiceMultithreadedTest {

    private BatchedTaskExecutorService service;

    // Configures an run that lasts a few seconds and typically leads to a mix
    // of maxBufferSize and maxPendingTime-based executions.
    private static final int MAX_BUFFER_SIZE = 10;
    private static final int MAX_PENDING_TIME_SECONDS = 1;
    private static final int NUM_THREADS = 20;
    private static final int NUM_ITERATIONS = 5;
    private static final int MAX_DELAY = 4000;

    @Before
    public void init() {
        BatchedTaskExecutorServiceConfig config = new BatchedTaskExecutorServiceConfig(MAX_BUFFER_SIZE, MAX_PENDING_TIME_SECONDS);
        service = new BatchedTaskExecutorService(new ArrayListTaskBufferFactory(), new PooledBatchedTaskExecutor(new DelayedBatchTaskExecutorFactory(2000), 3, 5), config);
    }

    @Test
    @Ignore
    public void testMultithreaded() throws InterruptedException {
        List<Callable<Object>> workers = new ArrayList<>(NUM_THREADS);
        for (int i = 0; i < NUM_THREADS; i++) {
            workers.add(Executors.callable(new SchedulerTask()));
        }
        ExecutorService threads = Executors.newFixedThreadPool(NUM_THREADS);
        threads.invokeAll(workers);
        service.shutdown();
        assertEquals((NUM_THREADS * NUM_ITERATIONS), service.getTotalTasksExecuted());
        assertEquals(service.getTotalTasksExecuted(), DelayedBatchTaskExecutor.totalTasksExecuted);
    }

    class SchedulerTask implements Runnable {

        @Override
        public void run() {
            try {
                for (int i = 0; i < NUM_ITERATIONS; i++) {
                    Thread.sleep((int) (Math.random() * MAX_DELAY));
                    service.schedule(new Task(UUID.randomUUID().toString()));
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
