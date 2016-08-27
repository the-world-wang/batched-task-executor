package com.careykevin.batchedtaskexecutor;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * A unit of work that may be executed
 *
 * @author careykevin
 */
public class Task {

    private String id;

    private String payload;

    private Map<String, String[]> options;

    /**
     * @param id      An identifier for the Tasks
     * @param payload A payload to carry
     * @param options A map of name-values describing additional options for the Task
     */
    public Task(String id, String payload, Map<String, String[]> options) {

        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("id may not be null or empty");
        }

        this.id = id;
        this.payload = payload;
        this.options = options;
    }

    public Task(String id) {
        this(id, null, null);
    }

    public String getId() {
        return id;
    }

    public String getPayload() {
        return payload;
    }

    public Map<String, String[]> getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return "id=" + id;
    }
}
