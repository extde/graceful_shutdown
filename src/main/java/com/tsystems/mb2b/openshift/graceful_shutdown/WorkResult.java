package com.tsystems.mb2b.openshift.graceful_shutdown;

public class WorkResult {

    private final long id;

    public WorkResult(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
