package io.github.no.today.aliyun.rocketmq;

import org.springframework.core.env.Environment;

/**
 * describe:
 * MQ 根据 TAG 区分环境
 *
 * <p>
 *
 * @author cheng
 * @date 2019/3/27 10:44
 */
class MQEnvUtil {

    private static final String DEV = "dev";

    /**
     * 处理开发环境的pid
     */
    static String handleGroupId(Environment env, String groupId) {
        if (env.acceptsProfiles(DEV)) {
            return groupId + "_DEV";
        } else {
            return groupId;
        }
    }

    /**
     * 处理开发环境的tag
     */
    static String handleTag(Environment env, String tag) {
        if (env.acceptsProfiles(DEV)) {
            return tag + "_DEV";
        } else {
            return tag;
        }
    }
}
