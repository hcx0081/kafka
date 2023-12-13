package com.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * {@code @description:}
 */
public class MyTransaction {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.100.100:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "123");
        
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        
        producer.initTransactions();
        producer.beginTransaction();
        
        try {
            for (int i = 0; i < 5; i++) {
                producer.send(new ProducerRecord<>("one", "hello world"));
            }
            
            // 模拟故障
            int i = 1 / 0;
            
            producer.commitTransaction();
        } catch (Exception e) {
            producer.abortTransaction();
        } finally {
            producer.close();
        }
    }
}
