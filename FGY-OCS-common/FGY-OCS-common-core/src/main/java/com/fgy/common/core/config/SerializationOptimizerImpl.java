package com.fgy.common.core.config;

import com.fgy.common.core.domain.OcsMessage;
import com.fgy.common.core.domain.info.SessionInfo;
import com.fgy.common.core.domain.info.UserInfo;
import com.fgy.common.core.result.CommonResult;
import org.apache.dubbo.common.serialize.support.SerializationOptimizer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author fgy
 * description 提前将序列化类注入dubbo中
 * date 2023/5/31 10:55
 */
public class SerializationOptimizerImpl implements SerializationOptimizer {
    @Override
    public Collection<Class<?>> getSerializableClasses() {
        List<Class<?>> classes = new LinkedList<>();
        classes.add(UserInfo.class);
        classes.add(SessionInfo.class);
        classes.add(OcsMessage.class);
        classes.add(CommonResult.class);
        return classes;
    }
}
