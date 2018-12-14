package cn.tgw.common.exceptionHandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/*
 * @Project:tgw
 * @Description:businessman controller exception handler
 * @Author:TjSanshao
 * @Create:2018-12-14 10:57
 *
 **/
@ControllerAdvice(basePackages = "cn.tgw.businessman.controller")
public class BusinessmanExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String businessmanControllerExceptionHandler(HttpServletRequest request) {
        request.setAttribute("javax.servlet.error.status_code", 500);
        return "forward:/error";
    }

}
