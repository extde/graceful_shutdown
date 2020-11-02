package com.tsystems.mb2b.openshift.graceful_shutdown;

import java.util.HashMap;
import java.util.Map;

import javax.jms.ConnectionFactory;
import javax.jms.Session;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.jms.client.HornetQJMSConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableJms
@Slf4j
public class JmsConfig {

    private static final String HOST = "localhost";
    private static final String PORT = "5445";
    private static final String QUEUE = "testQueue";


    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        try {
            ConnectionFactory cf = connectionFactory();
            factory.setConnectionFactory(cf);
            factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        } catch (Exception e) {
            log.error(null, e);
        }
        return factory;
    }

    @Bean // <-- important for health check
          // allows to have just one connection factory instead of two
    public ConnectionFactory connectionFactory() {
        log.info("Init HornetQJMSConnectionFactory with host: {}, and port: {}",
                HOST,
                PORT);

        Map<String, Object> jmsParameters = new HashMap<>();
        jmsParameters.put("host", HOST);
        jmsParameters.put("port", PORT);

        return new HornetQJMSConnectionFactory(false,
                new TransportConfiguration(NettyConnectorFactory.class.getName(),
                        jmsParameters)
        );
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setDefaultDestinationName(QUEUE);
        return jmsTemplate;
    }

}
