package cn.tgw.common.mapper;

import cn.tgw.common.model.SmsVerify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/*
 * @Project:tgw
 * @Description:message verify mapper
 * @Author:TjSanshao
 * @Create:2018-11-28 17:45
 *
 **/
@Mapper
public interface SmsVerifyMapper {

    @Select("select * from tgw_sms_verify")
    public List<SmsVerify> smsVerifyList();

}
