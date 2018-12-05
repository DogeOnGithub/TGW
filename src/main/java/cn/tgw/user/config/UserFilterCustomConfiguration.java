package cn.tgw.user.config;

import cn.tgw.user.filter.UserAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * @Project:tgw
 * @Description:custom filter config
 * @Author:TjSanshao
 * @Create:2018-11-30 16:13
 *
 **/
@Configuration
public class UserFilterCustomConfiguration {

    @Bean
    public FilterRegistrationBean userAuthenticationFilterRegistration(){
        FilterRegistrationBean<UserAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();

        //注入过滤器
        registrationBean.setFilter(new UserAuthenticationFilter());

        //添加过滤规则
        registrationBean.addUrlPatterns("/tjsanshao/user/detail");
        registrationBean.addUrlPatterns("/tjsanshao/user/password");
        registrationBean.addUrlPatterns("/tjsanshao/order/*");

        //设置过滤器名称
        registrationBean.setName("UserLoginFilter");

        //设置过滤器顺序
        registrationBean.setOrder(1);

        return registrationBean;
    }

}
