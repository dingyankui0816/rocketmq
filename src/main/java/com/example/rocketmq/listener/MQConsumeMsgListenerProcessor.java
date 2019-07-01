package com.example.rocketmq.listener;

import com.alibaba.fastjson.JSON;
import com.example.rocketmq.service.ExceptionService;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MQConsumeMsgListenerProcessor implements MessageListenerConcurrently {
    @Autowired
    private ExceptionService exceptionService;
    /**
     * 默认list里只有一条消息，可通过设置consumeMessageBatchMaxSize参数批量接收消息
     * 不要抛异常，如果没有return CONSUME_SUCCESS , consumer会重新消费该消息，直到return CONSUME_SUCCESS
     * @param list
     * @param consumeConcurrentlyContext
     * @return
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        if (CollectionUtils.isEmpty(list)){
            log.info("接收到的消息为空，不处理，直接返回成功");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        MessageExt messageExt = list.get(0);
        log.info("接受到的消息为: {}",new String(messageExt.getBody()));
        if (messageExt.getTopic().equals(messageExt.getTopic())){
            if (messageExt.getTags().equals("dingTag")){
                int times = messageExt.getReconsumeTimes();
                exceptionService.getThrowable();
                if (times == 3){  //消息重试3次，如果不需要再次消费，则返回成功
                    log.info("接收到的消息为空，不处理，直接返回成功");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                System.out.println(messageExt.toString());
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
