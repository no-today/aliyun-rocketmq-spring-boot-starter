package io.github.no.today.aliyun.rocketmq;

import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * describe: 消息处理程序
 * <p>
 *
 * @author no-today
 * @date 2019/1/24 14:33
 */
public interface MQConsumer {

    Logger log = LoggerFactory.getLogger(MQConsumer.class);

    Map<String, MQConsumer> mqProcessorMap = new ConcurrentHashMap<>();

    void startConsumer();

    /**
     * 处理消息
     */
    boolean process(Message message, ConsumeContext contest);

    /**
     * 消费者线程数
     *
     * @return thread count
     */
    default int threadCount() {
        return 20;
    }
}
