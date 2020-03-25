package io.github.no.today.aliyun.rocketmq;


import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.shade.com.alibaba.fastjson.JSON;
import com.aliyun.openservices.shade.com.alibaba.fastjson.serializer.SerializerFeature;
import io.github.no.today.aliyun.rocketmq.config.RocketMQConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author no-today
 */
public class MQProducer {

    private static final Logger log = LoggerFactory.getLogger(MQProducer.class);
    private static final int DEFAULT_THREAD = 5;

    private final RocketMQConfigProperties rocketMQConfigProperties;
    private final Environment environment;

    private Executor executor;
    private Producer producer;

    public MQProducer(RocketMQConfigProperties rocketMQConfigProperties, Environment environment) {
        this.rocketMQConfigProperties = rocketMQConfigProperties;
        this.environment = environment;
        this.executor = Executors.newFixedThreadPool(DEFAULT_THREAD);
    }

    @PostConstruct
    private void initMQ() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.AccessKey, rocketMQConfigProperties.getAccess());
        properties.put(PropertyKeyConst.SecretKey, rocketMQConfigProperties.getSecret());
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, rocketMQConfigProperties.getSendMsgTimeOutMillis());
        properties.put(PropertyKeyConst.NAMESRV_ADDR, rocketMQConfigProperties.getAddr());
        producer = ONSFactory.createProducer(properties);
        producer.start();
    }

    public void sendMessage(String topic, String tag, String key, Object message) {
        executor.execute(() -> {
            try {
                String tag_ = MQEnvUtil.handleTag(environment, tag);
                Message msg = new Message(
                        topic,
                        tag_,
                        JSON.toJSONBytes(message, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullBooleanAsFalse));
                msg.setKey(key);

                try {
                    SendResult sendResult = producer.send(msg);
                    // 同步发送消息，只要不抛异常就是成功
                    if (sendResult != null) {
                        log.info(new Date() + " Send mq message success. Topic is: " + msg.getTopic() + ", Tag is: " + tag_ + " msgId is: " + sendResult.getMessageId());
                    }
                } catch (Exception e) {
                    // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理
                    log.info(new Date() + " Send mq message failed. Topic is: " + msg.getTopic() + ", Tag is: " + tag_, e);
                }
            } catch (Exception e) {
                log.error("[消息准备异常]", e);
            }
        });
    }


    public void sendTimingMessage(String topic, String tag, String key, Object message, Instant timing) {
        executor.execute(() -> {
            try {
                String tag_ = MQEnvUtil.handleTag(environment, tag);
                Message msg = new Message(
                        topic,
                        tag_,
                        JSON.toJSONBytes(message, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullBooleanAsFalse));
                msg.setKey(key);

                msg.setStartDeliverTime(timing.toEpochMilli());

                try {
                    SendResult sendResult = producer.send(msg);
                    // 同步发送消息，只要不抛异常就是成功
                    if (sendResult != null) {
                        log.info(new Date() + " Send mq message success. Topic is: " + msg.getTopic() + ", Tag is: " + tag_ + " msgId is: " + sendResult.getMessageId());
                    }
                } catch (Exception e) {
                    // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理
                    log.info(new Date() + " Send mq message failed. Topic is: " + msg.getTopic() + ", Tag is: " + tag_, e);
                }
            } catch (Exception e) {
                log.error("[消息准备异常]", e);
            }
        });
    }

    public void sendDelayMessage(String topic, String tag, String key, Object message, Long delayTime) {
        executor.execute(() -> {
            try {
                String tag_ = MQEnvUtil.handleTag(environment, tag);
                Message msg = new Message(
                        topic,
                        tag_,
                        JSON.toJSONBytes(message, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullBooleanAsFalse));
                msg.setKey(key);

                msg.setStartDeliverTime(delayTime);

                try {
                    SendResult sendResult = producer.send(msg);
                    // 同步发送消息，只要不抛异常就是成功
                    if (sendResult != null) {
                        log.info(new Date() + " Send mq message success. Topic is: " + msg.getTopic() + ", Tag is: " + tag_ + " msgId is: " + sendResult.getMessageId());
                    }
                } catch (Exception e) {
                    // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理
                    log.info(new Date() + " Send mq message failed. Topic is: " + msg.getTopic() + ", Tag is: " + tag_, e);
                }
            } catch (Exception e) {
                log.error("[消息准备异常]", e);
            }
        });
    }
}
