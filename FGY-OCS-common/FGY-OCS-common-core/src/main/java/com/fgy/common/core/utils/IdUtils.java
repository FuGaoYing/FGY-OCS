package com.fgy.common.core.utils;

/**
 * @author fgy
 * description
 * date 2023/5/23 17:16
 */
public class IdUtils{
    /**
     * 使用雪花算法生成id 机器及中心先写死
     */
    private static final SnowflakeIdGenerator GENERATOR = SnowflakeIdGenerator.getInstance(1, 2);


    public static Long getId() {
        return GENERATOR.nextId();
    }

}
