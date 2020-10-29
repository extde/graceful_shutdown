package com.tsystems.mb2b.openshift.graceful_shutdown;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkController {

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/dowork")
    public WorkResult doWork() {
        return new WorkResult(counter.incrementAndGet());
    }
}
