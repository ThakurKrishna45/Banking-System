package com.krishna.banking.kafka;

import com.krishna.banking.event.TransactionNotificationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerService {
    private final KafkaTemplate<String, TransactionNotificationEvent> kafkaTemplate;
    private static final String TOPIC= "banking-notification";
    public void send(TransactionNotificationEvent event){
        kafkaTemplate.send(TOPIC,event);
    }
}
