package io.github.no.today.aliyun.rocketmq.config;

import io.github.no.today.aliyun.rocketmq.AbstractMQConsumer;
import io.github.no.today.aliyun.rocketmq.ApplicationReadyEventListener;
import io.github.no.today.aliyun.rocketmq.MQProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author no-today
 * @date 2020/3/14 9:21
 */
@Configuration
@EnableConfigurationProperties(RocketMQConfigProperties.class)
@ConditionalOnWebApplication
public class RocketMQStarterAutoConfiguration {

    @Bean
    @ConditionalOnClass(MQProducer.class)
    public MQProducer mqProducer(RocketMQConfigProperties rocketMQConfigProperties) {
        return new MQProducer(rocketMQConfigProperties);
    }

    @Bean
    @ConditionalOnClass(AbstractMQConsumer.class)
    public ApplicationReadyEventListener applicationReadyEventListener() {
        return new ApplicationReadyEventListener();
    }
}
