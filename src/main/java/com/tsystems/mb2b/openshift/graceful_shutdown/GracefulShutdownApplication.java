package com.tsystems.mb2b.openshift.graceful_shutdown;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GracefulShutdownApplication {

    public static void main(String[] args) {
        SpringApplication.run(GracefulShutdownApplication.class, args);
    }
}
