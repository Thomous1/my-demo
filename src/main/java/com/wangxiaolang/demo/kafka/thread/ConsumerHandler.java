package com.wangxiaolang.demo.kafka.thread;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;

/**
 * @Author wangzuoyu1
 * @Description TODO
 * @Date 2021/7/2 15:36
 * @Version 1.0
 */
@Slf4j
public class ConsumerHandler {

    private final KafkaConsumer kafkaConsumer;
    private ExecutorService executors;

    private Semaphore semaphore;

    public static volatile ConcurrentHashMap<TopicPartition, OffsetAndMetadata> commitMessage = new ConcurrentHashMap<>();
    private List<Future<String>> taskList = new ArrayList<>();

    private final Integer maxPollRecords = 2000;
    private final String enableAutoCommit = "false";
    private final String autoOffsetReset = "latest";
    private final Integer time = 2000;

    public ConsumerHandler(String brokers, String groupId, String topic, String userName, String passWord, Semaphore semaphore) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put("security.protocol", "SASL_PLAINTEXT");
        props.put("sasl.mechanism", "PLAIN");
        props.put("sasl.jaas.config", String.format("org.apache.kafka.common.security.plain.PlainLoginModule required username=\"%s\" password=\"%s\";", userName, passWord));
        this.kafkaConsumer = new KafkaConsumer<>(props);
        kafkaConsumer.subscribe(Arrays.asList(topic), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> collection) {
                log.info("threadId = {}, onPartitionsRevoked.", Thread.currentThread().getId());
                kafkaConsumer.commitSync(commitMessage);
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> collection) {
                log.info("threadId = {}, onPartitionsAssigned.", Thread.currentThread().getId());
                commitMessage.clear();
                //清空taskList列表
                taskList.clear();
            }
        });
        this.semaphore = semaphore;
    }

    public void execute(int num) {
        executors = new ThreadPoolExecutor(num, 2 * num, 2000L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1000), new ThreadPoolExecutor.CallerRunsPolicy());
        while (true) {
            try {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(time));
                for (ConsumerRecord record : records) {
                    semaphore.acquire();
                    executors.submit(new ConsumerWorker(record));
                    //提交任务到线程池处理
                    taskList.add(executors.submit(new ConsumerWorker(record)));
                    //判断kafka消息是否处理完成
                    for (Future<String> task : taskList) {
                        //阻塞，直到消息处理完成
                        task.get();
                    }
                    //同步向kafka集群中提交offset
                    kafkaConsumer.commitSync();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } finally {
                taskList.clear();
            }
        }
    }

    public void shutdown() {
        if (kafkaConsumer != null) {
            kafkaConsumer.close();
        }
        if (executors != null) {
            executors.shutdown();
        }
        try {
            if (!executors.awaitTermination(10, TimeUnit.SECONDS)) {
                System.out.println("Timeout.... Ignore for this case");
            }
        } catch (InterruptedException ignored) {
            System.out.println("Other thread interrupted this shutdown, ignore for this case.");
            Thread.currentThread().interrupt();
        }
    }
}
