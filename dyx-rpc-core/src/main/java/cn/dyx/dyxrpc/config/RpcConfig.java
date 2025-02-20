package cn.dyx.dyxrpc.config;

import cn.dyx.dyxrpc.loadbalancer.LoadBalancer;
import cn.dyx.dyxrpc.loadbalancer.LoadBalancerFactory;
import cn.dyx.dyxrpc.loadbalancer.LoadBalancerKeys;
import cn.dyx.dyxrpc.serializer.SerializerKeys;
import lombok.Data;

/**
 * RPC 框架配置
 */
@Data
public class RpcConfig {

    /**
     * 名称
     */
    private String name = "dyx-rpc";

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";

    /**
     * 服务器端口号
     */
    private Integer serverPort = 8080;

    /**
     * 模拟调用
     */
    private boolean mock = false;

    /**
     * 序列化器
     */
    private String serializer = SerializerKeys.JSON;

    /**
     * 注册中心配置
     */
    private RegistryConfig registryConfig = new RegistryConfig();

    /**
     * 负载均衡器配置
     */

    private String loadBalancer = LoadBalancerKeys.ROUND_ROBIN;

}

