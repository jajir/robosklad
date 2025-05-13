package com.example;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;

public class ArtemisJmsQueueTest {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String QUEUE_NAME = "roboti";
    private static final String USER_NAME = "admin";
    private static final String USER_PASSWORD = "admin";

    @Test
    public void testSendAndReceive() throws Exception {
        // Create connection factory
        try (ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
                BROKER_URL);
                JMSContext context = factory.createContext(USER_NAME,
                        USER_PASSWORD, JMSContext.AUTO_ACKNOWLEDGE)) {

            Queue queue = context.createQueue(QUEUE_NAME);

            // Send a message
            final String text = "Hello Artemis!";
            for (int i = 0; i < 10; i++) {
                context.createProducer().send(queue, text + " " + i);
            }
            recieveAllMessages(context);
        }
    }

    private void recieveAllMessages(final JMSContext context) throws Exception {
        // Create connection factory
        Queue queue = context.createQueue(QUEUE_NAME);
        // Receive all messages
        JMSConsumer consumer = context.createConsumer(queue);
        String message;
        while ((message = consumer.receiveBody(String.class, 1000)) != null) {
            System.out.println("Received: " + message);
        }
    }

}