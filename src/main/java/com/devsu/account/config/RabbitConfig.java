package com.devsu.account.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;
/**
 * Configures RabbitMQ beans for the account service.
 * <p>
 * This class is responsible for declaring the queues, exchanges, and bindings
 * required for the application to communicate with other services via RabbitMQ.
 * </p>
 */
@Configuration
public class RabbitConfig {
    public static final String CLIENT_CREATE_QUEUE = "client.created.queue";

    /**
     * Declares the queue for listening to client creation events.
     * <p>
     * When a new client is created in the client service, a message is sent to this queue.
     * The account service listens to this queue to perform subsequent actions,
     * such as creating an initial account for the new client.
     * </p>
     *
     * @return A durable Queue bean named "client.created.queue".
     */
    @Bean
    public Queue clientCreatedQueue() {
        return new Queue(CLIENT_CREATE_QUEUE);
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
