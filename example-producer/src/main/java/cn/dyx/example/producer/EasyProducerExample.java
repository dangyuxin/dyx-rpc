package cn.dyx.example.producer;

import cn.dyx.dyxrpc.RpcApplication;
import cn.dyx.dyxrpc.config.RegistryConfig;
import cn.dyx.dyxrpc.config.RpcConfig;
import cn.dyx.dyxrpc.model.ServiceMetaInfo;
import cn.dyx.dyxrpc.registry.LocalRegistry;
import cn.dyx.dyxrpc.registry.Registry;
import cn.dyx.dyxrpc.registry.RegistryFactory;
import cn.dyx.dyxrpc.server.HttpServer;
import cn.dyx.dyxrpc.server.VertxHttpServer;
import cn.dyx.example.common.service.UserService;

public class EasyProducerExample {
    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();

        // 注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
