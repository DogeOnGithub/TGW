package cn.tgw;

import cn.tgw.common.mapper.SmsVerifyMapper;
import cn.tgw.user.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TgwApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private SmsVerifyMapper smsVerifyMapper;

	@Test
	public void test01(){
		System.out.println(userMapper.selectByPrimaryKey(1));
	}

	@Test
	public void testSmsVerifyMapper(){
		System.out.println(smsVerifyMapper.smsVerifyList());
	}

	@Test
	public void contextLoads() {
	}

}
