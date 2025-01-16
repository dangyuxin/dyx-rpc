package cn.dyx.example.consumer;

import cn.dyx.dyxrpc.config.RpcConfig;
import cn.dyx.dyxrpc.proxy.ServiceProxyFactory;
import cn.dyx.dyxrpc.utils.ConfigUtils;
import cn.dyx.example.common.model.User;
import cn.dyx.example.common.service.UserService;
import cn.hutool.json.JSON;

public class EasyConsumerExample {
    public static void main(String[] args) {
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);

        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class,"rpc");
        System.out.println(rpc);

        User user = new User();
        user.setName("dangyuxin");
        System.out.println(userService.getUser(user));

        System.out.println(userService.getNumber());
    }
}
