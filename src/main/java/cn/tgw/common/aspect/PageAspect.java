package cn.tgw.common.aspect;

import cn.tgw.order.model.Order;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 * @Project:tgw
 * @Description:page helper aspect
 * @Author:TjSanshao
 * @Create:2018-12-12 09:14
 *
 **/
@Aspect
@Component
public class PageAspect {

    @Pointcut("@annotation(cn.tgw.common.annotation.OrderPageAnnotation)")
    public void pageDivide(){}

    @Around("pageDivide()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        PageHelper.startPage((int)args[0], (int)args[1]);
        try{
            Object list = joinPoint.proceed();
            return new PageInfo<Order>((List<Order>)list);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

}
