package cn.tgw.user.service;

import cn.tgw.user.model.User;

public interface UserService {

    /**
     * @Description:用于登录业务，根据用户名、密码、账号状态查询用户
     * @Param:[username, password, status]
     * @Return:cn.tgw.user.model.User
     * @Author:TjSanshao
     * @Date:2018-11-26
     * @Time:17:24
     */
    User getUserByUsernameAndPasswordAndStatus(String username, String password, Byte status);

}
