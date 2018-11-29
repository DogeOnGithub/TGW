package cn.tgw.common.mapper;

import cn.tgw.common.model.SmsVerify;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    @Select("select * from tgw_sms_verify where mobile=#{mobile}")
    public SmsVerify selectByMobile(String mobile);

    @Insert("insert into tgw_sms_verify(`mobile`,`code`,`status`,`send_time`,`times`) values(#{mobile},#{code},#{status},#{sendTime},#{times})")
    public int insertSmsVerify(SmsVerify smsVerify);

    @Update("update tgw_sms_verify set `code`=#{code},`status`=#{status},`send_time`=#{sendTime},`times`=#{times} where `mobile`=#{mobile}")
    public int updateCodeSmsVerify(SmsVerify smsVerify);

    @Update("update tgw_sms_verify set `status`=#{status} where `mobile`=#{mobile}")
    public int updateCodeStatusSmsVerify(SmsVerify smsVerify);

}
