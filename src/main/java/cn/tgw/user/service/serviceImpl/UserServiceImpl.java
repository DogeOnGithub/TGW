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

    /*
     * @Description:根据用户名和密码或者根据mobile和密码查询用户，如果两者中存在用户记录，返回用户记录，否则返回null
     * @Param:[username, password, status]
     * @Return:cn.tgw.user.model.User
     * @Author:TjSanshao
     * @Date:2018-12-04
     * @Time:08:29
     **/
    @Override
    @Cacheable(cacheNames = {"userCache"}, cacheManager = "userCacheManager")
    public User getUserByUsernameOrMobileAndPasswordAndStatus(String username, String password, Byte status) {

        //传进来的username可能是用户名，也可以是mobile，因此要做两次判断
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setUserStatus(status);

        //参数username有可能是手机号码
        user.setMobile(username);

        User userByUsername = userMapper.selectByUsernameAndPasswordAndUserStatus(user);

        //如果根据用户名和密码查询不到用户，那么就根据mobile和密码查询用户，如果都为空，返回null
        return userByUsername == null ? userMapper.selectByMobileAndPasswordAndUserStatus(user) : userByUsername;
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
    public boolean userRegister(User user) {

        //创建一个和user的id对应的userDetail
        UserDetail userDetail = new UserDetail();
        userDetail.setMobile(user.getMobile());
        userDetail.setRegTime(new Date());   //设置注册时间
        userDetail.setNickName("tgw_" + user.getUsername());   //设置默认昵称

        user.setUserStatus(new Byte("1"));   //将用户状态设置为1，即正常可登录

        userMapper.insertSelective(user);

        userDetail.setTgwUserId(user.getId());   //设置用户id

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

        return userDetailMapper.selectByUserId(user.getId());
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

    @Override
    public UserDetail updateUserDetail(UserDetail userDetail) {
        int rows = userDetailMapper.updateByUserIdSelective(userDetail);
        return userDetailMapper.selectByUserId(userDetail.getTgwUserId());
    }
}
