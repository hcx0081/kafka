package com.kafka.hello.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * {@code @description:}
 */
public class MyParam {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.100.100:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, "33554432");
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, "16384");
        props.put(ProducerConfig.LINGER_MS_CONFIG, "1");
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        
        for (int i = 0; i < 5; i++) {
            producer.send(new ProducerRecord<>("one", "hello world"), (metadata, exception) -> {
                if (exception == null) {
                    System.out.println("主题：" + metadata.topic() + "\t" +
                            "分区：" + metadata.partition() + "\t" +
                            "时间戳：" + metadata.timestamp());
                }
            });
        }
        
        producer.close();
    }
}
