package cn.tgw.user.service.serviceImpl;

import cn.tgw.user.mapper.UserDetailMapper;
import cn.tgw.user.mapper.UserMapper;
import cn.tgw.user.model.User;
import cn.tgw.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @program:tgw
 * @descrption:user service implement
 * @author:TjSanshao
 * @create:2018-11-26 17:24
 **/

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDetailMapper userDetailMapper;

    @Override
    @Cacheable(cacheNames = {"userCache"}, key = "#username", cacheManager = "userCacheManager")
    public User getUserByUsernameAndPasswordAndStatus(String username, String password, Byte status) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setUserStatus(status);

        return userMapper.selectByUsernameAndPasswordAndUserStatus(user);
    }
}
