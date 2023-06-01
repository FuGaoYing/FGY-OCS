package com.fgy.customer;

import com.corundumstudio.socketio.SocketIOClient;
import com.fgy.customer.listener.SocketIoListener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author fgy
 * description
 * date 2023/5/25 15:52
 */
@SpringBootTest
public class CustomerServiceApplicationTest {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void test() {
    }
}
