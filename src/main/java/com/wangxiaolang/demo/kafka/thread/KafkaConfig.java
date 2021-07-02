package com.wangxiaolang.demo.kafka.thread;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Semaphore;

/**
 * @Author wangzuoyu1
 * @Description TODO
 * @Date 2021/7/2 17:56
 * @Version 1.0
 */
@ConfigurationProperties(prefix = "kafka.consumer")
@Configuration
public class KafkaConfig {
    private String brokers;
    private String groupId;
    private Integer maxPollRecords;
    private Integer timeOut;
    private String enableAutoCommit;
    private String autoOffsetReset;
    private String topic;
    private String userName;
    private String passWord;
    private Integer semaphoreNum;

    public String getBrokers() {
        return brokers;
    }

    public void setBrokers(String brokers) {
        this.brokers = brokers;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getMaxPollRecords() {
        return maxPollRecords;
    }

    public void setMaxPollRecords(Integer maxPollRecords) {
        this.maxPollRecords = maxPollRecords;
    }

    public Integer getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

    public String getEnableAutoCommit() {
        return enableAutoCommit;
    }

    public void setEnableAutoCommit(String enableAutoCommit) {
        this.enableAutoCommit = enableAutoCommit;
    }

    public String getAutoOffsetReset() {
        return autoOffsetReset;
    }

    public void setAutoOffsetReset(String autoOffsetReset) {
        this.autoOffsetReset = autoOffsetReset;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Integer getSemaphoreNum() {
        return semaphoreNum;
    }

    public void setSemaphoreNum(Integer semaphoreNum) {
        this.semaphoreNum = semaphoreNum;
    }

    @Bean(name = "consumerHandler")
    public ConsumerHandler consumerHandler() {
        return new ConsumerHandler(brokers, groupId, topic, userName, passWord, new Semaphore(semaphoreNum));
    }
}
