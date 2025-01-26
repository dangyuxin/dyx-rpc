package cn.dyx.dyxrpc.proxy;

import cn.dyx.dyxrpc.RpcApplication;
import cn.dyx.dyxrpc.config.RpcConfig;
import cn.dyx.dyxrpc.constant.RpcConstant;
import cn.dyx.dyxrpc.model.RpcRequest;
import cn.dyx.dyxrpc.model.RpcResponse;
import cn.dyx.dyxrpc.model.ServiceMetaInfo;
import cn.dyx.dyxrpc.registry.Registry;
import cn.dyx.dyxrpc.registry.RegistryFactory;
import cn.dyx.dyxrpc.serializer.Serializer;
import cn.dyx.dyxrpc.serializer.SerializerFactory;
import cn.dyx.dyxrpc.server.tcp.VertxTcpClient;
import cn.hutool.core.collection.CollUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 服务代理（JDK 动态代理）
 */
public class ServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
            // 从注册中心获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }
            ServiceMetaInfo selectedServiceMetaInfo = serviceMetaInfoList.get(0);
            System.out.println("服务信息--> "+selectedServiceMetaInfo.getServiceNodeKey());
            // 发送 TCP 请求
            RpcResponse rpcResponse = VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo);
            return rpcResponse.getData();
        } catch (Exception e) {
            System.out.println("error:"+e.getMessage());
            throw new RuntimeException("调用失败");
        }
    }

}
