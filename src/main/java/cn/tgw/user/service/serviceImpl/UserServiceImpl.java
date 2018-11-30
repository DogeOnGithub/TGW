package cn.tgw.user.service.serviceImpl;

import cn.tgw.common.mapper.SmsVerifyMapper;
import cn.tgw.common.model.SmsVerify;
import cn.tgw.user.mapper.UserDetailMapper;
import cn.tgw.user.mapper.UserMapper;
import cn.tgw.user.model.User;
import cn.tgw.user.model.UserDetail;
import cn.tgw.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/*
 * @Project:tgw
 * @Description:user service implement
 * @Author:TjSanshao
 * @Create:2018-11-26 17:24
 *
 **/

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDetailMapper userDetailMapper;

    @Autowired
    private SmsVerifyMapper smsVerifyMapper;

    @Override
    @Cacheable(cacheNames = {"userCache"}, cacheManager = "userCacheManager")
    public User getUserByUsernameAndPasswordAndStatus(String username, String password, Byte status) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setUserStatus(status);

        return userMapper.selectByUsernameAndPasswordAndUserStatus(user);
    }

    @Override
    public boolean enableUserRegister(User user) {
        //判断用户名是否存在
        if (userMapper.selectByUsername(user) != null){
            //用户名已存在，不可注册
            return false;
        }

        //用户名未存在，可以注册
        return true;
    }

    @Override
    public boolean enableMoblieRegister(String mobile) {
        //判断手机号是否已经使用
        if (userDetailMapper.selectByMobile(mobile) != null){
            return false;
        }

        //手机号未使用，可以注册
        return true;
    }

    @Override
    @Transactional
    public boolean userRegister(User user, UserDetail userDetail) {
        user.setUserStatus(new Byte("1"));
        userMapper.insertSelective(user);
        userDetail.setTgwUserId(user.getId());
        userDetail.setRegTime(new Date());
        userDetailMapper.insertSelective(userDetail);

        //更新验证码状态
        SmsVerify smsVerify = new SmsVerify();
        smsVerify.setMobile(userDetail.getMobile());
        smsVerify.setStatus(new Byte("1"));
        smsVerifyMapper.updateCodeStatusSmsVerify(smsVerify);

        return true;
    }

    @Override
    @Cacheable(cacheNames = {"userCache"}, cacheManager = "userCacheManager")
    public UserDetail getUserDetailByUserId(User user) {

        //查询指定用户的用户信息，包括id
        User queryUser = userMapper.selectByUsername(user);

        return userDetailMapper.selectByUserId(queryUser.getId());
    }

    @Override
    @CachePut(cacheNames = {"userCache"}, cacheManager = "userCacheManager")
    @Transactional
    public User updateUserPassword(User user) {

        userMapper.updateByPrimaryKeySelective(user);
        return userMapper.selectByPrimaryKey(user.getId());

    }

    @Override
    @Cacheable(cacheNames = {"userCache"}, cacheManager = "userCacheManager")
    public User getUserById(int id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
