package com.tsystems.mb2b.openshift.graceful_shutdown;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GracefulShutdownApplication {

    public static void main(String[] args) {
        SpringApplication.run(GracefulShutdownApplication.class, args);
    }

}