package cn.tgw;

import cn.tgw.common.mapper.SmsVerifyMapper;
import cn.tgw.common.model.SmsVerify;
import cn.tgw.common.service.MiaoDiService;
import cn.tgw.user.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.TimeZone;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TgwApplicationTests {

	//tgw_user
	@Autowired
	private UserMapper userMapper;

	//tgw_sms_verify表
	@Autowired
	private SmsVerifyMapper smsVerifyMapper;

	//秒嘀业务
	@Autowired
	private MiaoDiService miaoDiService;

	/*
	 * @Description:测试UserMapper
	 * @Param:[]
	 * @Return:void
	 * @Author:TjSanshao
	 * @Date:2018-11-28
	 * @Time:21:43
	 **/
	@Test
	public void testUserMapper(){
		System.out.println(userMapper.selectByPrimaryKey(1));
	}

	/*
	 * @Description:测试SmsVerifyMapper
	 * @Param:[]
	 * @Return:void
	 * @Author:TjSanshao
	 * @Date:2018-11-28
	 * @Time:21:43
	 **/
	@Test
	public void testSmsVerifyMapper(){
		System.out.println(smsVerifyMapper.smsVerifyList());
		System.out.println(smsVerifyMapper.selectByMobile("13420120424"));

		SmsVerify smsVerify = new SmsVerify();
		smsVerify.setMobile("17607591628");
		smsVerify.setCode(miaoDiService.generateCode(6));
		smsVerify.setStatus(new Byte("0"));
		smsVerify.setSendTime(new Date());
		smsVerify.setTimes(new Byte("1"));
		System.out.println(smsVerifyMapper.insertSmsVerify(smsVerify));

		System.out.println(new Date());

	}

	/*
	 * @Description:测试update方法
	 * @Param:[]
	 * @Return:void
	 * @Author:TjSanshao
	 * @Date:2018-11-29
	 * @Time:09:06
	 **/
	@Test
	public void testSmsVerifyMapper2(){
		SmsVerify smsVerify = smsVerifyMapper.selectByMobile("17607591628");
		smsVerify.setCode(miaoDiService.generateCode(6));
		smsVerify.setStatus(new Byte("0"));
		smsVerify.setSendTime(new Date());
		smsVerify.setTimes(new Byte(String.valueOf(smsVerify.getTimes().intValue() + 1)));

		System.out.println(smsVerifyMapper.updateCodeSmsVerify(smsVerify));
	}

	@Test
	public void testSmsVerifyMapper3(){
		SmsVerify smsVerify = smsVerifyMapper.selectByMobile("17607591628");
		smsVerify.setStatus(new Byte("1"));
		System.out.println(smsVerifyMapper.updateCodeStatusSmsVerify(smsVerify));
	}

	@Test
	public void testMiaodiService(){
		System.out.println(miaoDiService.generateCode(6));
		try {
			System.out.println(miaoDiService.executeWithTemplateId("13420120424", miaoDiService.generateCode(6)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void contextLoads() {
	}

}
