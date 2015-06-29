package com.flake.batchedtaskexecutor;

/**
 * A factory for creating new TaskBufferFactory objects
 * 
 * @author careykevin
 *
 */
public interface TaskBufferFactory {

	TaskBuffer newTaskBuffer();
}
