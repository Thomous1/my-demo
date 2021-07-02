package com.wangxiaolang.demo.kafka.controller;

import com.wangxiaolang.demo.kafka.thread.ConsumerHandler;
import com.wangxiaolang.demo.security.entity.ResultModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author wangzuoyu1
 * @Description TODO
 * @Date 2021/7/1 11:26
 * @Version 1.0
 */

@RestController
@Slf4j
public class TestKafkaController {

    @Resource(name = "myKafkaConsumer")
    private KafkaConsumer consumer;

    @Resource(name = "consumerHandler")
    private ConsumerHandler consumerHandler;

    @Value("${kafka.consumer.poll.timeout}")
    private long time;



    @GetMapping("/kafka/test")
    public ResultModel getKafkaData() {
        new Thread(new KafkaConsumerService()).start();
        return ResultModel.ok("拉取kafka数据  线程已经启动~ ");
    }

    class KafkaConsumerService implements Runnable {
        @Override
        public void run() {
            int emptyCounter = 0;
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(time));
                if (records.isEmpty()) {
                    log.info("goto sleep! emptyCounter{}", ++emptyCounter);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                } else {
                    Map<TopicPartition, OffsetAndMetadata> commitMessage = new HashMap<>();
                    for (ConsumerRecord<String, String> consumerRecord : records) {
                        // 消费kafka  手动提交offset
                        System.out.println(consumerRecord.value());
                        commitMessage.put(new TopicPartition(consumerRecord.topic(), consumerRecord.partition()),
                                new OffsetAndMetadata(consumerRecord.offset() + 1));
                    }
                    consumer.commitSync(commitMessage);
                }
            }
        }
    }

    @GetMapping("/kafka/batch/consumer")
    public void testConsumer() {
         consumerHandler.execute(6);
    }
}
