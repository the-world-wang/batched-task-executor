package com.flake.batchedtaskexecutor;

import java.util.List;

/**
 * A buffer for holding Tasks until they are executed
 * 
 * @author careykevin
 *
 */
public interface TaskBuffer {

	void add(Task task);
	
	int size();
	
	List<Task> getTasks();
}
