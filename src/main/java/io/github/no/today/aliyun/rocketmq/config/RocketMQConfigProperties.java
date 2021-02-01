package io.github.no.today.aliyun.rocketmq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rocket-mq")
public class RocketMQConfigProperties {

    /**
     * 阿里云 Access key
     */
    private String access;

    /**
     * 阿里云 Secret
     */
    private String secret;

    /**
     * TCP 协议客户端接入点
     */
    private String addr;

    /**
     * 发送消息超时时间
     */
    private String sendMsgTimeOutMillis = String.valueOf(3000);

    /**
     * 启动消费
     */
    private boolean enable = true;

    public String getAccess() {
        return access;
    }

    public RocketMQConfigProperties setAccess(String access) {
        this.access = access;
        return this;
    }

    public String getSecret() {
        return secret;
    }

    public RocketMQConfigProperties setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    public String getAddr() {
        return addr;
    }

    public RocketMQConfigProperties setAddr(String addr) {
        this.addr = addr;
        return this;
    }

    public String getSendMsgTimeOutMillis() {
        return sendMsgTimeOutMillis;
    }

    public RocketMQConfigProperties setSendMsgTimeOutMillis(String sendMsgTimeOutMillis) {
        this.sendMsgTimeOutMillis = sendMsgTimeOutMillis;
        return this;
    }

    public boolean isEnable() {
        return enable;
    }

    public RocketMQConfigProperties setEnable(boolean enable) {
        this.enable = enable;
        return this;
    }

    public Boolean getEnable() {
        return enable;
    }

    public RocketMQConfigProperties setEnable(Boolean enable) {
        this.enable = enable;
        return this;
    }
}
