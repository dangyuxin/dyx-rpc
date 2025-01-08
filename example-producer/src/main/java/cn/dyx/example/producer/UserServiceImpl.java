package cn.dyx.example.producer;

import cn.dyx.example.common.model.User;
import cn.dyx.example.common.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("用户名: " + user.getName());
        return user;
    }
}
