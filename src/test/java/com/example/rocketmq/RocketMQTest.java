package com.example.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(classes = {RocketmqApplication.class})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class RocketMQTest {
    /**使用RocketMq的生产者*/
    @Autowired
    private DefaultMQProducer defaultMQProducer;


    @Test
    public void send(){
        String msg = "demo msg test";
        log.info("开始发送消息："+msg);
        Message sendMsg = new Message("DingTopic","dingTag",msg.getBytes());
        //默认3秒超时
        SendResult sendResult = null;

        try {
            sendResult = defaultMQProducer.send(sendMsg);
        } catch (Exception e) {
            log.error("失败", e);
        }

        log.info("消息发送响应信息：{}", Objects.nonNull(sendResult) ? sendResult.toString() : "");
    }
}
