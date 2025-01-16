package cn.dyx.example.producer;

import cn.dyx.dyxrpc.RpcApplication;
import cn.dyx.dyxrpc.config.RpcConfig;
import cn.dyx.dyxrpc.registry.LocalRegistry;
import cn.dyx.dyxrpc.server.HttpServer;
import cn.dyx.dyxrpc.server.VertxHttpServer;
import cn.dyx.example.common.service.UserService;

public class EasyProducerExample {
    public static void main(String[] args) {

        RpcApplication.init();

        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 提供服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
