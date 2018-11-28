package cn.tgw.user.service;

import cn.tgw.user.model.User;

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

}
