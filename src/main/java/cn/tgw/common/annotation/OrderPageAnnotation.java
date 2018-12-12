package cn.tgw.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * @Project:tgw
 * @Description:order divide
 * @Author:TjSanshao
 * @Create:2018-12-12 09:33
 *
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderPageAnnotation {
}
