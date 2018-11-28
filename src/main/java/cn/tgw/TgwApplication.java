package cn.tgw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ServletComponentScan//开启后台检测的servlet配置，比如actutor的配置
@EnableTransactionManagement//开启数据库事务管理
//@MapperScan()//将项目中对应的mapper接口类的路径加进来就可以了,先注释了，不然运行不起来
//@EnableScheduling//开启定时任务，需要用到再开
@SpringBootApplication
@MapperScan(basePackages = {"cn.tgw.user.mapper", "cn.tgw.common.mapper"})
public class TgwApplication {

	public static void main(String[] args) {
		SpringApplication.run(TgwApplication.class, args);
	}
}
