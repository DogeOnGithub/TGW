package cn.tgw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync //开启异步
@ServletComponentScan //开启后台检测的servlet配置，比如actutor的配置
@EnableTransactionManagement //开启数据库事务管理
//@EnableScheduling //开启定时任务，需要用到再开
@EnableRabbit //开启RabbitMQ
@SpringBootApplication
@MapperScan(basePackages = {"cn.tgw.user.mapper", "cn.tgw.businessman.mapper", "cn.tgw.common.mapper","cn.tgw.goods.mapper"})
public class TgwApplication {

	public static void main(String[] args) {
		SpringApplication.run(TgwApplication.class, args);
	}
}
