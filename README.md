# 阿里云 RocketMQ 自动配置

## 使用姿势

### 引入依赖

```xml
<!-- RocketMQ Starter -->
<dependency>
    <groupId>io.github.no-today</groupId>
    <artifactId>aliyun-rocketmq-spring-boot-starter</artifactId>
    <version>1.1.RELEASE</version>
</dependency>
```

### 配置

```yaml
application:
  rocket-mq:
    access: Key
    secret: secret
    send-msg-time-out-millis: 3000 # 发送消息超时时间
    addr: # TCP 协议客户端接入点
    enable: true # 是否开启消费
```

### 生产

直接注入 **MQProducer** 即可发送消息

### 消费

继承 **AbstractMQConsumer** 实现方法即可

```java
@Slf4j
@Component
public class TaskEventConsumer extends AbstractMQConsumer {

    @Override
    protected String getTopic() {
        return MQConstant.DOMAIN_TOPIC;
    }

    @Override
    protected String getTag() {
        return MQConstant.TAG;
    }

    @Override
    public boolean process(Message message, ConsumeContext consumeContext) {
        DTO taskEventData = JSON.parseObject(new String(message.getBody()), DTO.class);
        
        // 处理任务

        // 消费失败时会重发
        return true;
    }
}
```
