package cn.tgw.businessman.config;

/*
 * @Project:tgw
 * @Description:businessman custom filter configuration
 * @Author:TjSanshao
 * @Create:2018-12-03 09:46
 *
 **/

import cn.tgw.businessman.filter.BusinessmanAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BusinessmanFilterCustomConfiguration {

    @Bean
    public FilterRegistrationBean businessmanAuthenticationFilterRegistration(){
        FilterRegistrationBean<BusinessmanAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();

        //设置过滤器
        registrationBean.setFilter(new BusinessmanAuthenticationFilter());

        //添加过滤规则
        registrationBean.addUrlPatterns("/businessman/test");

        //设置过滤器名称
        registrationBean.setName("BusinessmanLoginFilter");

        //设置过滤器顺序
        registrationBean.setOrder(1);

        return registrationBean;
    }

}
