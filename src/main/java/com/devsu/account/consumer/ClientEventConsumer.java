package com.devsu.account.consumer;

import com.devsu.account.config.RabbitConfig;
import com.devsu.account.dto.ClientCreatedEvent;
import com.devsu.account.entity.Account;
import com.devsu.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Consumer for handling events related to clients from the client service.
 * <p>
 * This class listens to a RabbitMQ queue for messages about client-related events,
 * such as client creation, and processes them accordingly.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class ClientEventConsumer {
    private static final Logger log = LoggerFactory.getLogger(ClientEventConsumer.class);
    private final AccountRepository accountRepository;

    /**
     * Handles the {@link ClientCreatedEvent} received from the RabbitMQ queue.
     * <p>
     * This method is annotated with {@code @RabbitListener} to automatically receive
     * messages from the "client.created.queue". When a new client is created in the
     * client service, this method is triggered.
     * </p>
     *
     * @param event The event object containing details of the newly created client.
     */
    @RabbitListener(queues = RabbitConfig.CLIENT_CREATE_QUEUE)
    public void handleClientCreated(ClientCreatedEvent event) {
        log.info("Received ClientCreatedEvent for client: {}", event);
        accountRepository.save(Account.builder()
                        .initialBalance(event.getInitialBalance())
                        .accountNumber(event.getAccountNumber())
                        .accountType(event.getAccountType())
                        .status(true)
                        .client(event.getName())
                .build());
        log.info("Account created for client: {}", event.getName());
    }
}
