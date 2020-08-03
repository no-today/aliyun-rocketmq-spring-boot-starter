package io.github.no.today.aliyun.rocketmq;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import io.github.no.today.aliyun.rocketmq.config.RocketMQConfigProperties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Properties;

/**
 * @author no-today
 * @date 2019/1/24 14:37
 */
public abstract class AbstractMQConsumer implements MQConsumer {

    @Resource
    protected RocketMQConfigProperties rocketMQConfigProperties;

    protected abstract String getTopic();

    protected abstract String getTag();

    private String getGroupId() {
        return "GID_" + getTag();
    }

    @Override
    public void startConsumer() {
        final Properties properties = new Properties();

        properties.put(PropertyKeyConst.AccessKey, rocketMQConfigProperties.getAccess());
        properties.put(PropertyKeyConst.SecretKey, rocketMQConfigProperties.getSecret());
        properties.put(PropertyKeyConst.NAMESRV_ADDR, rocketMQConfigProperties.getAddr());
        properties.put(PropertyKeyConst.GROUP_ID, getGroupId());
        properties.put(PropertyKeyConst.ConsumeThreadNums, threadCount());

        final Consumer consumer = ONSFactory.createConsumer(setProperties(properties));
        consumer.subscribe(getTopic(), getTag(), (message, context) -> {
            log.debug("[Rocket-MQ] - Receive: " + message);
            return process(message, context) ? Action.CommitMessage : Action.ReconsumeLater;
        });

        if (rocketMQConfigProperties.getEnable()) {
            consumer.start();
            log.info("[正在监听] - gid: {}", getGroupId());
        } else {
            log.info("[关闭监听] - gid: {}", getGroupId());
        }
    }

    @PostConstruct
    void init() {
        mqProcessorMap.put(getTopic() + "-" + getTag(), this);
    }

    /**
     * 自定义配置
     * <p>
     * 默认仅配置 GID 和消费者线程数
     *
     * @see com.aliyun.openservices.ons.api.PropertyKeyConst
     */
    protected Properties setProperties(Properties properties) {
        return properties;
    }
}
