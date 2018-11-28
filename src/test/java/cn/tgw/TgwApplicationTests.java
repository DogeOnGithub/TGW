package cn.tgw;

import cn.tgw.common.mapper.SmsVerifyMapper;
import cn.tgw.common.service.MiaoDiService;
import cn.tgw.user.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
