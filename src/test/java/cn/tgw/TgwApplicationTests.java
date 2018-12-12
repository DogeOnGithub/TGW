package cn.tgw;

import cn.tgw.businessman.mapper.BusinessmanDetailMapper;
import cn.tgw.businessman.mapper.BusinessmanMapper;
import cn.tgw.businessman.model.Businessman;
import cn.tgw.common.mapper.SmsVerifyMapper;
import cn.tgw.common.model.SmsVerify;
import cn.tgw.common.service.MiaoDiService;
import cn.tgw.common.utils.MD5Utils;
import cn.tgw.common.utils.QiniuUtil;
import cn.tgw.config.AlipayConfiguration;
import cn.tgw.order.mapper.OrderMapper;
import cn.tgw.order.model.Order;
import cn.tgw.order.service.OrderService;
import cn.tgw.user.mapper.UserDetailMapper;
import cn.tgw.user.mapper.UserMapper;
import cn.tgw.user.model.User;
import cn.tgw.user.model.UserDetail;
import cn.tgw.user.service.UserService;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TgwApplicationTests {

	//tgw_user
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserDetailMapper userDetailMapper;

	//tgw_sms_verify表
	@Autowired
	private SmsVerifyMapper smsVerifyMapper;

	@Autowired
	private BusinessmanMapper businessmanMapper;

	@Autowired
	private BusinessmanDetailMapper businessmanDetailMapper;

	//秒嘀业务
	@Autowired
	private MiaoDiService miaoDiService;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private UserService userService;

	@Autowired
	private QiniuUtil qiniuUtil;

	@Autowired
	private OrderService orderService;

	@Autowired
	private AlipayConfiguration alipayConfiguration;

	@Autowired
	private OrderMapper orderMapper;

	@Test
	public void testUserMapper(){
		User user = new User();
		user.setUsername("TjSanshao");

		System.out.println(userMapper.selectByPrimaryKey(1));
		System.out.println(userMapper.selectByUsername(user));
	}

	@Test
	public void testUserMapper2(){
		User user = new User();
		user.setUsername("testRegister");
		user.setPassword("testRegister");
		System.out.println(userMapper.insertSelective(user));
		System.out.println(user);
	}

	@Test
	public void testUserDetailMapper(){
		System.out.println(userDetailMapper.selectByMobile("13420120424"));
	}


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
	public void testCalendar(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		System.out.println(calendar.get(Calendar.MINUTE));
	}

	@Test
	public void testRabbitMQ(){
		rabbitTemplate.convertAndSend("tgw.verifycode.relay.exchange", "", "15521634926");
	}

	@Test
	public void testRabbitMQ2(){
		System.out.println(rabbitTemplate.receiveAndConvert("tgw.verifycode.queue"));
	}

	@Test
	public void testMd5(){
		System.out.println(MD5Utils.tgwMD5("password"));
	}

	@Test
	public void testUserDetailMapper2(){
		UserDetail userDetail = new UserDetail();
		userDetail.setTgwUserId(6);
		userDetail.setLastUpdateTime(new Date());

		System.out.println(userDetailMapper.updateByUserIdSelective(userDetail));
	}

	@Test
	public void testUserDetailMapper3(){
		UserDetail userDetail = new UserDetail();
		userDetail.setTgwUserId(6);
		userDetail.setSex(new Byte("0"));
		userDetail.setMobile("17607591628");
		userDetail.setEmail("testupdatebyuserid@test.com");
		userDetail.setLastUpdateTime(new Date());
		userDetail.setNickName("tgw_" + userDetail.getMobile());
		System.out.println(userDetailMapper.updateByUserIdSelective(userDetail));
	}

	@Test
	public void testUserDetailMapper4(){
		System.out.println(userDetailMapper.selectByUserId(6));
		System.out.println(userDetailMapper.selectByPrimaryKey(4));
		System.out.println(userDetailMapper.selectByMobile("17607591628"));
	}

	@Test
	public void testBusinessmanMapper(){
		System.out.println(businessmanMapper.selectByPrimaryKey(1));
		System.out.println(businessmanDetailMapper.selectByPrimaryKey(2));
	}

	@Test
	public void testUserService(){
		System.out.println(userService.getUserById(1));
	}

	@Test
	public void testBusinessmanMapper2(){
		Businessman businessman = new Businessman();
		businessman.setUsername("TjSanshao");
		System.out.println(businessmanMapper.selectByUsername(businessman));
	}

	@Test
	public void testBusinessmanDetailMapper(){
		System.out.println(businessmanDetailMapper.selectByBusinessmanId(1));
	}

	@Test
	public void testBusinessmanDetailMapper2(){
		System.out.println(businessmanDetailMapper.selectByPrimaryKey(3));
	}

	@Test
	public void testBusinessmanDetailMapper3(){
		System.out.println(businessmanDetailMapper.selectBusinessmanByShopSettleStatus(new Byte("0")));
		System.out.println(businessmanDetailMapper.selectAllBusinessmanDetail());
	}

	@Test
	public void testBusinessmanDetailMapper4(){
		System.out.println(businessmanDetailMapper.selectByContactPhone("98765432101"));
//		System.out.println(businessmanDetailMapper.selectBusinessmanByShopSettleStatus(new Byte("0")));
//		System.out.println(businessmanDetailMapper.selectAllBusinessman());
	}

	@Test
	public void testNewUserMapper(){
		System.out.println(userMapper.selectByPrimaryKey(1));
	}

	@Test
	public void testNewBusinessmanMapper(){
		System.out.println(businessmanMapper.selectByPrimaryKey(2));

		Businessman businessman = new Businessman();
		businessman.setId(1);
		businessman.setMobile("12345678901");
		System.out.println(businessmanMapper.updateByPrimaryKeySelective(businessman));
	}

	@Test
	public void testNewBusinessmanMapper2(){
		Businessman businessman = new Businessman();
		businessman.setMobile("13420120424");
		businessman.setPassword("666666");
		businessman.setStatus(new Byte("1"));
		System.out.println(businessmanMapper.selectByMobileAndPasswordAndStatus(businessman));
	}

	@Test
	public void tjsanshaoTestQiniu(){
		try {
			File leimu = new File("C:\\Users\\TjSanshao\\Pictures\\Saved Pictures\\雷姆001.jpg");
			FileInputStream inputStream = new FileInputStream(leimu);
			MultipartFile multipartFile = new MockMultipartFile(leimu.getName(), inputStream);
			System.out.println(QiniuUtil.tjsanshaoUploadImage(multipartFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testOrderService(){
		System.out.println(orderService.getAllOrders());
		System.out.println(orderService.getAllOrdersByUserId(1));
		System.out.println("test");
	}

	@Test
	public void testOrderService2(){
		System.out.println(orderService.createOrderWithUserIdAndGoodsId(1, 1, 10));
	}

	@Test
	public void testOrderService3(){
		System.out.println(orderService.getOrdersByUserIdAndOrderSellStatusAndStatusNormal(1, new Byte("1")));
	}

	@Test
	public void testAlipay(){
		System.out.println(alipayConfiguration.getCommonUrlPrefix());
		System.out.println("test");

		System.out.println("-----------------------------------");

		AlipayClient alipayClient = new DefaultAlipayClient(alipayConfiguration.getGatewayUrl(), alipayConfiguration.getApp_id(), alipayConfiguration.getMerchant_private_key(), "json", alipayConfiguration.getCharset(), alipayConfiguration.getAlipay_public_key(), alipayConfiguration.getSign_type());
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(alipayConfiguration.getReturn_url());
		alipayRequest.setNotifyUrl(alipayConfiguration.getNotify_url());

		//商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no = "13420120424";
		//付款金额，必填
		String total_amount = "99";
		//订单名称，必填
		String subject = "test";
		//商品描述，可空
		String body = "test test";

		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
				+ "\"total_amount\":\""+ total_amount +"\","
				+ "\"subject\":\""+ subject +"\","
				+ "\"body\":\""+ body +"\","
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

		try {
			String result = alipayClient.pageExecute(alipayRequest).getBody();
			System.out.println(result);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testOrderMapper(){
		System.out.println(orderMapper.selectByPrimaryKey(2));

		Order order = new Order();
		order.setId(1);
		order.setTotal(new BigDecimal(500));
		order.setUniqueOrderNumber(Long.toString(new Date().getTime()));
		System.out.println(orderMapper.updateByPrimaryKeySelective(order));
	}

	@Test
	public void testOrderServiceRabbitListener(){
		rabbitTemplate.convertAndSend("tgw.ordertime.relay.exchange", "", 1);
	}

	@Test
	public void testOrderServiceCreateOrder(){
		System.out.println(orderService.createmsKillOrderByUserIdAndmsKillId(1, 1, 1));
	}

	@Test
	public void testOrderServiceOrderDivide(){
		System.out.println(orderService.getOrdersByBusinessmanIdWithPage(1, 2, 1));
	}

	@Test
	public void contextLoads() {
	}

}
