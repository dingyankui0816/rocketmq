package com.example.rocketmq.config;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "rocketmq.producer")
public class MQProducerConfig {
    private String groupName;
    private String namesrvAddr;
    private Integer maxMessageSize;
    private Integer sendMsgTimeout;
    private Integer retryTimeWhenSendFailed;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public Integer getMaxMessageSize() {
        return maxMessageSize;
    }

    public void setMaxMessageSize(Integer maxMessageSize) {
        this.maxMessageSize = maxMessageSize;
    }

    public Integer getSendMsgTimeout() {
        return sendMsgTimeout;
    }

    public void setSendMsgTimeout(Integer sendMsgTimeout) {
        this.sendMsgTimeout = sendMsgTimeout;
    }

    public Integer getRetryTimeWhenSendFailed() {
        return retryTimeWhenSendFailed;
    }

    public void setRetryTimeWhenSendFailed(Integer retryTimeWhenSendFailed) {
        this.retryTimeWhenSendFailed = retryTimeWhenSendFailed;
    }
    @Bean
    public DefaultMQProducer getRocketMQProducer(){
        DefaultMQProducer producer;
        producer = new DefaultMQProducer(groupName);
        //如果需要同一个jvm中不同的producer往不同的mq集群中发送消息，需要设置不同的instanceName
        producer.setNamesrvAddr(namesrvAddr);
        producer.setMaxMessageSize(maxMessageSize);
        producer.setSendMsgTimeout(sendMsgTimeout);
        producer.setRetryTimesWhenSendFailed(retryTimeWhenSendFailed);
        try {
            producer.start();
            log.info("producer is start ! groupName:{},namesrvAddr:{}",this.groupName,this.namesrvAddr);
        } catch (MQClientException e) {
            log.error(String.format("producer is error {}"
                    , e.getMessage(),e));
        }
        return producer;
    }
}
