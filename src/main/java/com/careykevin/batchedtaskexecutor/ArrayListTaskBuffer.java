package com.careykevin.batchedtaskexecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements a TaskBuffer backed by an ArrayList
 *
 * @author careykevin
 */
public class ArrayListTaskBuffer implements TaskBuffer {

    private final List<Task> tasks;

    public ArrayListTaskBuffer() {
        tasks = new ArrayList<>();
    }

    @Override
    public void add(Task task) {
        tasks.add(task);
    }

    @Override
    public int size() {
        return tasks.size();
    }

    @Override
    public List<Task> getTasks() {
        return tasks;
    }
}
