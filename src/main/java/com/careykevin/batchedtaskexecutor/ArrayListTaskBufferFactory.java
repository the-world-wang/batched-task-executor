package com.careykevin.batchedtaskexecutor;

/**
 * Creates new instances of ArrayListTaskBuffer objects
 *
 * @author careykevin
 */
public class ArrayListTaskBufferFactory implements TaskBufferFactory {

    @Override
    public TaskBuffer newTaskBuffer() {
        return new ArrayListTaskBuffer();
    }
}
