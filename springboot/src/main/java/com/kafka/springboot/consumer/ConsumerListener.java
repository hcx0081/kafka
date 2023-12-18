package com.kafka.springboot.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * {@code @description:} Kafka消费者
 */
@Component
public class ConsumerListener {
    @KafkaListener(topics = "one")
    public void consumeData(String msg) {
        System.out.println(msg);
    }
}
