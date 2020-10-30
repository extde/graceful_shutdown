package com.tsystems.mb2b.openshift.graceful_shutdown;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class WorkController {

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/dowork")
    public WorkResult doWork() throws InterruptedException {
        log.info("dowork enter");
        Thread.sleep(60_000);
        log.info("dowork exit");
        return new WorkResult(counter.incrementAndGet());
    }
}
