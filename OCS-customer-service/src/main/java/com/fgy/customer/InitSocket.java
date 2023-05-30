package com.fgy.customer;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * @author fgy
 * description
 * date 2023/5/26 10:44
 */
@Component
public class InitSocket implements CommandLineRunner {
    @Autowired
    private SocketIOServer server;

    @Override
    public void run(String... args) throws Exception {
        server.start();
    }
}
