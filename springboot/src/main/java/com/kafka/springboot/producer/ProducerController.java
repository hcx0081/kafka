package com.kafka.springboot.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * {@code @description:} Kafka生产者
 */
@RestController
public class ProducerController {
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;
    
    @RequestMapping("/sendMsg")
    public String sendMsg(String msg) {
        kafkaTemplate.send("one", msg);
        return "ok";
    }
}
