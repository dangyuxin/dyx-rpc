package cn.dyx.example.producer;

import cn.dyx.dyxrpc.registry.LocalRegistry;
import cn.dyx.dyxrpc.server.HttpServer;
import cn.dyx.dyxrpc.server.VertxHttpServer;
import cn.dyx.example.common.service.UserService;

public class EasyProducerExample {
    public static void main(String[] args) {

        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 提供服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8091);
    }
}
