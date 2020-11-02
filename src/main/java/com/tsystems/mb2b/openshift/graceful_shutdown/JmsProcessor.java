package com.tsystems.mb2b.openshift.graceful_shutdown;

import java.nio.charset.StandardCharsets;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JmsProcessor {

    private final JmsTemplate jmsTemplate;

    public JmsProcessor(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = "testQueue")
    public void onMessage(Message m) throws JMSException {
        log.info("Message received: {}\n{}", m.getJMSDestination(), extractResponseBody(m));
    }

    public void sendBinary(String request) {
        jmsTemplate.send(session -> {
            try {
                BytesMessage bytesMessage = session.createBytesMessage();
                bytesMessage.writeBytes(request.getBytes(StandardCharsets.UTF_8));
                return bytesMessage;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private String extractResponseBody(Message m) throws JMSException {
        if (m instanceof TextMessage) {
            return m.getBody(String.class);
        } else if (m instanceof BytesMessage) {
            BytesMessage msg = (BytesMessage) m;
            byte[] bytes = new byte[(int) msg.getBodyLength()];
            msg.readBytes(bytes);
            msg.reset(); // housekeeping - return read pointer to 0
            return new String(bytes, StandardCharsets.UTF_8);
        } else {
            throw new RuntimeException("Unsupported message type: " + m.getClass().getName());
        }
    }
}
