package com.wangxiaolang.demo.kafka.thread;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

/**
 * @Author wangzuoyu1
 * @Description TODO
 * @Date 2021/7/2 15:33
 * @Version 1.0
 */

public class ConsumerWorker implements Callable<String> {

    private final ConsumerRecord<String, String>  record;

    private Semaphore semaphore;

    public ConsumerWorker(ConsumerRecord record) {
        this.record = record;
    }

    @Override
    public String call() throws Exception {
        try {
            //TODO 处理kafka消费记录逻辑
            System.out.println(Thread.currentThread().getName() + " consumed " + record.partition()
                    + "th message with offset: " + record.offset());
            ConsumerHandler.commitMessage.put(new TopicPartition(record.topic(), record.partition()),new OffsetAndMetadata(record.offset() + 1));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
        return "success";
    }
}
