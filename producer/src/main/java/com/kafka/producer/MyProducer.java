package com.kafka.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * {@code @description:}
 */
public class MyProducer {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.100.100:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        
        /* 异步发送 */
        // 没有回调函数的异步发送
        for (int i = 0; i < 5; i++) {
            producer.send(new ProducerRecord<>("one", "hello world"));
        }
        
        // 具有回调函数的异步发送
        for (int i = 0; i < 5; i++) {
            producer.send(new ProducerRecord<>("one", "hello world"), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        System.out.println("主题：" + metadata.topic() + "\t" +
                                "分区：" + metadata.partition() + "\t" +
                                "时间戳：" + metadata.timestamp());
                    }
                }
            });
        }
        
        
        /* 同步发送 */
        for (int i = 0; i < 5; i++) {
            producer.send(new ProducerRecord<>("one", "hello world")).get();
        }
        
        producer.close();
    }
}
