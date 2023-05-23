package com.fgy.common.core.utils;

import com.fgy.common.core.utils.SnowflakeIdGenerator;

/**
 * @author fgy
 * description
 * date 2023/5/23 17:16
 */
public class IdUtils{
    private static final SnowflakeIdGenerator GENERATOR = SnowflakeIdGenerator.getInstance(1, 2);


    public static Long getId() {
        return GENERATOR.nextId();
    }

}
