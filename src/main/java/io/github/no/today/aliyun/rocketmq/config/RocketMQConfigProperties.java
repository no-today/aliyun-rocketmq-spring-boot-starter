package io.github.no.today.aliyun.rocketmq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.rocket-mq")
public class RocketMQConfigProperties {
    private String access;
    private String secret;
    private String addr;
    private String sendMsgTimeOutMillis;
    private Boolean enable;

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

    public Boolean getEnable() {
        return enable;
    }

    public RocketMQConfigProperties setEnable(Boolean enable) {
        this.enable = enable;
        return this;
    }
}
