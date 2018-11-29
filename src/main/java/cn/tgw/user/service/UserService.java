package cn.tgw.user.service;

import cn.tgw.user.model.User;
import cn.tgw.user.model.UserDetail;

public interface UserService {

    /*
     * @Description:
     * @Param:[username, password, status]
     * @Return:cn.tgw.user.model.User
     * @Author:TjSanshao
     * @Date:2018-11-26
     * @Time:17:24
     **/
    User getUserByUsernameAndPasswordAndStatus(String username, String password, Byte status);

    /*
     * @Description:判断用户名是否可以注册
     * @Param:[user, mobile]
     * @Return:boolean
     * @Author:TjSanshao
     * @Date:2018-11-29
     * @Time:11:32
     **/
    boolean enableUserRegister(User user);

    /*
     * @Description:判断手机号是否可以注册
     * @Param:[mobile]
     * @Return:boolean
     * @Author:TjSanshao
     * @Date:2018-11-29
     * @Time:11:46
     **/
    boolean enableMoblieRegister(String mobile);

    boolean userRegister(User user, UserDetail userDetail);

}
