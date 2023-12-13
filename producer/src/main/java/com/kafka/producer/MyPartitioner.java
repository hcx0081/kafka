package com.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Map;
import java.util.Properties;

/**
 * {@code @description:}
 */
public class MyPartitioner implements Partitioner {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.100.100:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, MyPartitioner.class.getName());
        
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
    
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        String valueString = value.toString();
        int partition;
        if (valueString.contains("hello")) {
            partition = 0;
        } else {
            partition = 1;
        }
        return partition;
    }
    
    @Override
    public void close() {
    }
    
    @Override
    public void configure(Map<String, ?> configs) {
    
    }
}
